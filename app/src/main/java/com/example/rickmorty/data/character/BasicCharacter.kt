package com.example.rickmorty.data.character

data class BasicCharacter(
  val id: String,
  val name: String,
  val status: String?,
  val species: String?,
  val image: String?,
)