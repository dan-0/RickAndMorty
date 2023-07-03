package com.example.rickmorty.ui.screens.characterinfo

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.rickmorty.data.UiState
import com.example.rickmorty.data.character.FullCharacter

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CharacterInfo(
  state: State<UiState<FullCharacter?>>
) {
  AnimatedContent(targetState = state.value) {
    when(it) {
      is UiState.Error -> Text("Error")
      is UiState.Loading -> {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator()
        }
      }
      is UiState.Success -> {
        if (it.data == null) {
          Text("Could not find character")
        } else {
          Text(it.data.name)
        }
      }
    }
  }
}

