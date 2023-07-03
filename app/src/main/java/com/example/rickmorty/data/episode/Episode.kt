package com.example.rickmorty.data.episode

import com.example.rickmorty.persistence.entity.DbCharacter

data class Episode(
  val id: String,
  val name: String?,
  val airDate: String?,
  val episodeCode: String?,
  val characters: List<DbCharacter>,
  val created: String
)