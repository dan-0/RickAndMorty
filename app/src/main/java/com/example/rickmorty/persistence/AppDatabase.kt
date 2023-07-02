package com.example.rickmorty.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickmorty.data.character.DbCharacter

@Database(entities = [DbCharacter::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun characterDao(): CharacterDao
}