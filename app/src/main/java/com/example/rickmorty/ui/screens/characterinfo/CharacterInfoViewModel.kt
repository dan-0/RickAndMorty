package com.example.rickmorty.ui.screens.characterinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.character.FullCharacter
import com.example.rickmorty.persistence.entity.DbCharacter
import com.example.rickmorty.store.CharacterInfoStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import org.mobilenativefoundation.store.store5.impl.extensions.get
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharacterInfoViewModel @Inject constructor(
  private val characterInfoStore: CharacterInfoStore,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private val key: String = savedStateHandle.get<String>(KEY_CHARACTER_ID)!!

  private val _character = MutableStateFlow<CharacterInfoState>(CharacterInfoState.Loading)
  val character: StateFlow<CharacterInfoState> = _character

  init {
    viewModelScope.launch(Dispatchers.IO) {
      characterInfoStore.stream(StoreReadRequest.cached(key, true))
        .collect { response ->
          _character.value = when (response) {
            is StoreReadResponse.Data -> {
              val character = response.value.getOrNull()?.let {
                FullCharacter(
                  name = it.name,
                  status = it.status,
                  species = it.species,
                  subspecies = it.subspecies,
                  gender = it.gender,
                  image = it.image,
                  created = it.created
                )
              }
              if (character == null) {
                Timber.e("No character received from data")
                CharacterInfoState.Error
              } else {
                CharacterInfoState.Success(character)
              }
            }
            is StoreReadResponse.Error.Exception -> {
              Timber.e(response.error)
              try {
                val fullCharacter = dbCharacterToFullCharacter(characterInfoStore.get(key).getOrNull())

                if (fullCharacter != null) {
                  CharacterInfoState.Success(fullCharacter)
                } else {
                  CharacterInfoState.Error
                }
              } catch (e: Exception) {
                Timber.e(e)
                CharacterInfoState.Error
              }
            }
            is StoreReadResponse.Error.Message -> {
              try {
                val fullCharacter = dbCharacterToFullCharacter(characterInfoStore.get(key).getOrNull())

                if (fullCharacter != null) {
                  CharacterInfoState.Success(fullCharacter)
                } else {
                  Timber.e(response.message)
                  CharacterInfoState.Error
                }
              } catch (e: Exception) {
                Timber.e(e)
                CharacterInfoState.Error
              }
            }
            is StoreReadResponse.Loading -> CharacterInfoState.Loading
            is StoreReadResponse.NoNewData -> {
              Timber.e("Successful query with no new data")
              CharacterInfoState.Error
            }
          }
        }
    }
  }

  private fun dbCharacterToFullCharacter(dbCharacter: DbCharacter?): FullCharacter? {
    dbCharacter ?: return null
    return FullCharacter(
      name = dbCharacter.name,
      status = dbCharacter.status,
      species = dbCharacter.species,
      subspecies = dbCharacter.subspecies,
      gender = dbCharacter.gender,
      image = dbCharacter.image,
      created = dbCharacter.created
    )
  }

  companion object {
    const val KEY_CHARACTER_ID = "characterId"
  }
}

