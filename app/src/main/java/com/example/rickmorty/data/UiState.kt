package com.example.rickmorty.data

sealed class UiState<Data> {
  class Loading<Data> : UiState<Data>()

  data class Success<Data>(
    val data: Data
  ) : UiState<Data>()

  data class Error<Data>(
    val errorMessage: String? = null,
    val throwable: Throwable? = null,
  ) : UiState<Data>()
}