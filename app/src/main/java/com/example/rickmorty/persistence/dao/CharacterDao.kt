package com.example.rickmorty.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.rickmorty.persistence.entity.DbCharacter

@Dao
interface CharacterDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun updateCharacters(characters: List<DbCharacter>)

  @Update
  fun updateCharacter(characters: DbCharacter)

  @Query("SELECT * FROM character")
  fun getAll(): List<DbCharacter>

  @Query("SELECT * FROM character WHERE id IN (:id)")
  fun getCharacterById(id: String): DbCharacter?
}

