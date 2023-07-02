package com.example.rickmorty.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.data.UiState
import com.example.rickmorty.data.character.BasicCharacter
import com.example.rickmorty.store.CharacterStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
  private val characterStore: CharacterStore
) : ViewModel() {

  private val _characters = MutableStateFlow<UiState<List<BasicCharacter>>>(
    UiState.Loading()
  )

  val characters: StateFlow<UiState<List<BasicCharacter>>> = _characters

  init {
    viewModelScope.launch(Dispatchers.IO) {
      characterStore.stream(
        StoreReadRequest.cached("characterStore", true)
      ).collect { storeResponse ->
        Timber.d("storeResponse $storeResponse")
        val uiState = when (storeResponse) {
          is StoreReadResponse.Data -> {
            val data = storeResponse.dataOrNull()?.map {
              BasicCharacter(
                it.id,
                it.name,
                it.status,
                it.species,
                it.image
              )
            } ?: listOf()
            UiState.Success(data.toImmutableList())
          }
          is StoreReadResponse.Error.Exception -> {
            Timber.e(storeResponse.error, "Store exception")
            UiState.Error(throwable = storeResponse.error)
          }
          is StoreReadResponse.Error.Message -> {
            UiState.Error(errorMessage = storeResponse.message)
          }
          is StoreReadResponse.Loading -> {
            UiState.Loading()
          }
          is StoreReadResponse.NoNewData -> UiState.Success(listOf())
        }
        _characters.value = uiState
      }
    }
  }

}