package com.example.rickmorty.ui.screens.charachters

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickmorty.R
import com.example.rickmorty.data.character.BasicCharacter
import com.example.rickmorty.ui.screens.shared.BasicCharacterLabel
import com.example.rickmorty.ui.screens.shared.CharacterImage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CharactersScreen(
  modifier: Modifier = Modifier,
  state: State<CharactersState>,
  characterSelected: (characterId: String) -> Unit
) {
  AnimatedContent(state.value) { currentState ->
    when (currentState) {
      is CharactersState.Error -> {
        Text("Error")
      }

      is CharactersState.Loading -> {
        Box(
          modifier = Modifier.fillMaxSize(),
          contentAlignment = Alignment.Center
        ) {
          CircularProgressIndicator()
        }
      }

      is CharactersState.Success -> {
        if (currentState.characters.isEmpty()) {
          Text("No characters found")

        } else {
          LazyColumn(
            modifier = modifier.fillMaxSize()
          ) {
            items(currentState.characters) {
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
      if (basicCharacter.image != null) {
        CharacterImage(basicCharacter.image, Modifier.fillMaxWidth())
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

