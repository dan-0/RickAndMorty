package com.example.rickmorty.data.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FullCharacter(
  val name: String,
  val status: String?,
  val species: String?,
  val subspecies: String?,
  val gender: String?,
  val image: String?,
  val created: String
) : Parcelable