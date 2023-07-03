package com.example.rickmorty.ui.screens.charachters

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.rickmorty.R
import com.example.rickmorty.data.UiState
import com.example.rickmorty.data.character.BasicCharacter

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CharactersScreen(
  modifier: Modifier = Modifier,
  state: State<UiState<List<BasicCharacter>>>,
  characterSelected: (characterId: String) -> Unit
) {
  AnimatedContent(state.value) { currentState ->
    when (currentState) {
      is UiState.Error -> {
        Text("Error")
      }
      is UiState.Loading -> {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator()
        }
      }
      is UiState.Success -> {
        if (currentState.data.isEmpty()) {
          Text("No characters found")

        } else {
          LazyColumn(
            modifier = modifier.fillMaxSize()
          ) {
            items(currentState.data) {
              BasicCharacterView(basicCharacter = it, characterSelected = characterSelected)
            }
          }
        }
      }
    }
  }
}

@Composable
fun BasicCharacterView(
  basicCharacter: BasicCharacter,
  characterSelected: (characterId: String) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable {
        characterSelected(basicCharacter.id)
      }
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(4.dp),
      modifier = Modifier.padding(8.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
      ) {
        var colorFilter by remember {
          mutableStateOf<ColorFilter?>(null)
        }
        if (basicCharacter.image != null) {
          AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
              .data(basicCharacter.image)
              .crossfade(true)
              .error(R.drawable.baseline_error_outline_24)
              .placeholder(R.drawable.baseline_refresh_24)
              .build(),
            contentDescription = "Character image",
            colorFilter = colorFilter,
            onLoading = { colorFilter = ColorFilter.tint(Color.DarkGray) },
            onError = { colorFilter = ColorFilter.tint(Color.Red) },
            onSuccess = { colorFilter = null },
            modifier = Modifier.height(150.dp)
          )
        }
      }
      BasicCharacterLabel(
        label = stringResource(R.string.label_name),
        value = basicCharacter.name 
      )
      BasicCharacterLabel(
        label = stringResource(id = R.string.label_status),
        value = basicCharacter.status
      )
      BasicCharacterLabel(
        label = stringResource(id = R.string.label_species),
        value = basicCharacter.species
      )
    }
  }
}

@Composable
fun BasicCharacterLabel(label: String, value: String?) {
  value ?: return
  Column {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium
    )
  }
}
