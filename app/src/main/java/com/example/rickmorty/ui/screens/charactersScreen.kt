package com.example.rickmorty.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.example.rickmorty.data.UiState
import com.example.rickmorty.data.character.BasicCharacter

@Composable
fun CharactersScreen(
  modifier: Modifier = Modifier,
  state: State<UiState<List<BasicCharacter>>>
) {
  LazyColumn(
    modifier = modifier.fillMaxSize()
  ) {
    when (val currentState = state.value) {
      is UiState.Error -> {
        item {
          Text("Error")
        }
      }
      is UiState.Loading -> item {
        CircularProgressIndicator()
      }
      is UiState.Success -> {
        if (currentState.data.isEmpty()) {
          item {
            Text("No characters found")
          }
        } else {
          items(currentState.data) {
            Text(it.name)
          }
        }
      }
    }
  }
}

