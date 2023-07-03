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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CharacterInfo(
  state: State<CharacterInfoState>
) {
  AnimatedContent(targetState = state.value) {
    when(it) {
      is CharacterInfoState.Error -> Text("Error")
      is CharacterInfoState.Loading -> {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator()
        }
      }
      is CharacterInfoState.Success -> {
        Text(it.character.name)
      }
    }
  }
}

