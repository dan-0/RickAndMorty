package com.example.rickmorty.ui.screens.characterinfo

import android.os.Parcelable
import com.example.rickmorty.data.character.FullCharacter
import kotlinx.parcelize.Parcelize

sealed class CharacterInfoState : Parcelable {
  @Parcelize
  object Loading : CharacterInfoState()
  @Parcelize
  object Error : CharacterInfoState()
  @Parcelize
  data class Success(val character: FullCharacter) : CharacterInfoState()
}