package com.example.rickmorty.di

import android.content.Context
import androidx.room.Room
import com.example.rickmorty.persistence.AppDatabase
import com.example.rickmorty.persistence.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

  @Provides
  @Singleton
  fun database(@ApplicationContext applicationContext: Context): AppDatabase {
    return Room.databaseBuilder(
      applicationContext,
      AppDatabase::class.java, "app-db"
    ).build()
  }

  @Provides
  @Singleton
  fun characterDao(appDatabase: AppDatabase): CharacterDao {
    return appDatabase.characterDao()
  }
}