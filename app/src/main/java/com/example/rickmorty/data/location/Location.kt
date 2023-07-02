package com.example.rickmorty.data.location

import com.example.rickmorty.data.character.DbCharacter

data class Location(
  val id: String,
  val name: String?,
  val type: String?,
  val dimension: String?,
  val residents: List<DbCharacter>,
  val created: String,
)