package com.example.rickmorty.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.rickmorty.CharacterPagesQuery
import com.example.rickmorty.CharactersByPageQuery
import com.example.rickmorty.FullCharacterQuery
import com.example.rickmorty.persistence.dao.CharacterDao
import com.example.rickmorty.persistence.entity.DbCharacter
import com.example.rickmorty.store.CharacterInfoStore
import com.example.rickmorty.store.CharacterStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.SourceOfTruth
import org.mobilenativefoundation.store.store5.StoreBuilder
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CharacterStoreModule {

  @Provides
  @Singleton
  fun characterStore(
    apolloClient: ApolloClient,
    characterDao: CharacterDao
  ): CharacterStore {
    val store = StoreBuilder.from<String, List<DbCharacter>, List<DbCharacter>>(
      fetcher = Fetcher.of {
        getCharactersFromAllPages(apolloClient)
      },
      sourceOfTruth = SourceOfTruth.of(
        reader = {
          flow {
            Timber.d("getting dao")
            emit(characterDao.getAll())
          }
        },
        writer = { _, characters ->
          Timber.d("Writing characters, count ${characters.size}")
          characters.chunked(999) {
            characterDao.updateCharacters(it)
          }
        }
      )
    ).build()

    return CharacterStore(store)
  }

  @Provides
  @Singleton
  fun characterInfoStore(
    apolloClient: ApolloClient,
    characterDao: CharacterDao
  ): CharacterInfoStore {
    val store = StoreBuilder.from<String, Optional<DbCharacter>, Optional<DbCharacter>>(
      fetcher = Fetcher.of {
        apolloClient.query(FullCharacterQuery(it)).execute().let { response ->
          val character = response.data?.character ?: return@of Optional.absent()

          val dbCharacter = DbCharacter(
            id = character.fullCharacterInfo.basicCharacterInfo.id ?: return@of Optional.absent(),
            name = character.fullCharacterInfo.basicCharacterInfo.name ?: return@of Optional.absent(),
            status = character.fullCharacterInfo.basicCharacterInfo.status,
            species = character.fullCharacterInfo.basicCharacterInfo.species,
            subspecies = character.fullCharacterInfo.type,
            gender = character.fullCharacterInfo.gender,
            image = character.fullCharacterInfo.basicCharacterInfo.image,
            created = character.fullCharacterInfo.created ?: return@of Optional.absent()
          )
          Optional.present(dbCharacter)
        }
      },
      sourceOfTruth = SourceOfTruth.of(
        reader = { key ->
          flowOf(Optional.presentIfNotNull(characterDao.getCharacterById(key)))
        },
        writer = { _, dbCharacter ->
          dbCharacter.getOrNull()?.let {
            characterDao.updateCharacter(it)
          }
        }
      )
    ).build()
    return CharacterInfoStore(store)
  }

  private suspend fun getCharactersFromAllPages(apolloClient: ApolloClient): List<DbCharacter> {
    val apolloResponse = apolloClient.query(CharacterPagesQuery()).execute()

    Timber.d("fetching pages")
    val pages = apolloResponse.data?.characters?.info?.pages ?: 1

    val characterPage = mutableListOf<Deferred<List<DbCharacter>?>>()

    coroutineScope {
      (1..pages).forEach { currentPage ->
        characterPage.add(
          async {
            val result =
              apolloClient.query(CharactersByPageQuery(Optional.presentIfNotNull(currentPage))).execute()
                .data?.characters?.results

            result?.mapNotNull { currentResult ->
              currentResult ?: return@mapNotNull null
              val info = currentResult.fullCharacterInfo
              DbCharacter(
                id = info.basicCharacterInfo.id ?: return@mapNotNull null,
                name = info.basicCharacterInfo.name ?: return@mapNotNull null,
                status = info.basicCharacterInfo.status,
                species = info.basicCharacterInfo.species,
                image = info.basicCharacterInfo.image,
                subspecies = info.type,
                gender = info.gender,
                created = info.created ?: return@mapNotNull null,
              )
            }
          }
        )
      }
    }

    val characters = characterPage.mapNotNull { it.await() }.flatten()
    Timber.d("Received characters count ${characters.size}")
    return characters
  }
}

