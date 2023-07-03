package com.example.rickmorty.ui.screens.characterinfo

import com.example.rickmorty.data.character.FullCharacter

sealed class CharacterInfoState {
  object Loading : CharacterInfoState()
  object Error : CharacterInfoState()
  data class Success(val character: FullCharacter) : CharacterInfoState()
}