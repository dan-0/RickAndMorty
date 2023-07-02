package com.example.rickmorty.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickmorty.data.character.DbCharacter

@Dao
interface CharacterDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun updateCharacters(characters: List<DbCharacter>)

  @Query("SELECT * FROM character")
  fun getAll(): List<DbCharacter>

  @Query("SELECT * FROM character WHERE id IN (:id)")
  fun getCharacterById(id: String): DbCharacter?
}

