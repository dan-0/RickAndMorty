package com.example.rickmorty.ui.screens.charachters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.character.BasicCharacter
import com.example.rickmorty.persistence.entity.DbCharacter
import com.example.rickmorty.store.CharacterStore
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
class CharactersViewModel @Inject constructor(
  private val characterStore: CharacterStore
) : ViewModel() {

  private val _characters = MutableStateFlow<CharactersState>(
    CharactersState.Loading
  )

  val characters: StateFlow<CharactersState> = _characters

  init {
    viewModelScope.launch(Dispatchers.IO) {
      characterStore.stream(
        StoreReadRequest.cached(KEY_ALL_CHARACTERS, true)
      ).collect { storeResponse ->
        Timber.d("storeResponse $storeResponse")
        val uiState = when (storeResponse) {
          is StoreReadResponse.Data -> {
            val data = dbCharacterToBasicCharacter(storeResponse.value)
            CharactersState.Success(data)
          }
          is StoreReadResponse.Error.Exception -> {
            Timber.e(storeResponse.error, "Store exception")
            try {
              CharactersState.Success(
                dbCharacterToBasicCharacter(characterStore.get(KEY_ALL_CHARACTERS))
              )
            } catch (e: Exception) {
              Timber.e(e)
              CharactersState.Error
            }
          }
          is StoreReadResponse.Error.Message -> {
            Timber.e("Store error: ${storeResponse.message}")
            try {
              CharactersState.Success(
                dbCharacterToBasicCharacter(characterStore.get(KEY_ALL_CHARACTERS))
              )
            } catch (e: Exception) {
              Timber.e(e)
              CharactersState.Error
            }
          }
          is StoreReadResponse.Loading -> {
            CharactersState.Loading
          }
          is StoreReadResponse.NoNewData -> CharactersState.Success(listOf())
        }
        _characters.value = uiState
      }
    }
  }

  private fun dbCharacterToBasicCharacter(
    characters: List<DbCharacter>
  ): List<BasicCharacter> {
    return characters.map {
      BasicCharacter(
        it.id,
        it.name,
        it.status,
        it.species,
        it.image
      )
    }
  }

  companion object {
    private const val KEY_ALL_CHARACTERS = "characterStore"
  }
}