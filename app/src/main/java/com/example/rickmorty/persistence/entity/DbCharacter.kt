package com.example.rickmorty.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("character")
data class DbCharacter(
  @PrimaryKey
  @ColumnInfo("id")
  val id: String,
  @ColumnInfo("name")
  val name: String,
  @ColumnInfo("status")
  val status: String?,
  @ColumnInfo("species")
  val species: String?,
  @ColumnInfo("subspecies")
  val subspecies: String?,
  @ColumnInfo("gender")
  val gender: String?,
  @ColumnInfo("image")
  val image: String?,
  @ColumnInfo("created")
  val created: String
)

