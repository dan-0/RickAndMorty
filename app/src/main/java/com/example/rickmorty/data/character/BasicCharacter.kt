package com.example.rickmorty.data.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasicCharacter(
  val id: String,
  val name: String,
  val status: String?,
  val species: String?,
  val image: String?,
) : Parcelable