package com.example.rickmorty.ui.screens.charachters

import com.example.rickmorty.data.character.BasicCharacter

sealed class CharactersState {
  object Loading : CharactersState()
  object Error : CharactersState()
  data class Success(
    val characters: List<BasicCharacter>
  ) : CharactersState()
}