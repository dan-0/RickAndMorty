package com.example.rickmorty.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickmorty.persistence.dao.CharacterDao
import com.example.rickmorty.persistence.entity.DbCharacter

@Database(entities = [DbCharacter::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun characterDao(): CharacterDao
}