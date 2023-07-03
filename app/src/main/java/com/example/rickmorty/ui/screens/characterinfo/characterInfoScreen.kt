package com.example.rickmorty.ui.screens.characterinfo

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickmorty.R
import com.example.rickmorty.data.character.FullCharacter
import com.example.rickmorty.ui.screens.shared.BasicCharacterLabel
import com.example.rickmorty.ui.screens.shared.CharacterImage

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
        CharacterInfo(it.character)
      }
    }
  }
}

@Composable
fun CharacterInfo(character: FullCharacter) {
  Column(
    modifier = Modifier
      .wrapContentHeight()
      .padding(8.dp),
    verticalArrangement = Arrangement.spacedBy(4.dp),
  ) {
    if (character.image != null) {
      CharacterImage(imageUrl = character.image, modifier = Modifier.fillMaxWidth())
    }
    BasicCharacterLabel(label = stringResource(id = R.string.label_name), value = character.name)
    BasicCharacterLabel(label = stringResource(id = R.string.label_status), value = character.status)
    BasicCharacterLabel(label = stringResource(id = R.string.label_species), value = character.species)
    BasicCharacterLabel(label = stringResource(id = R.string.label_subspecies), value = character.subspecies)
    BasicCharacterLabel(label = stringResource(id = R.string.label_gender), value = character.gender)
  }
}

