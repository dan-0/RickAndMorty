package com.example.rickmorty.ui.screens.charachters

import android.os.Parcelable
import com.example.rickmorty.data.character.BasicCharacter
import kotlinx.parcelize.Parcelize

sealed class CharactersState : Parcelable {
  @Parcelize
  object Loading : CharactersState()
  @Parcelize
  object Error : CharactersState()

  @Parcelize
  data class Success(
    val characters: List<BasicCharacter>
  ) : CharactersState()
}