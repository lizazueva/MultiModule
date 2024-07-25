package com.example.multimodule.di

import android.content.Context
import androidx.room.Room
import com.example.data.db.Dao
import com.example.multimodule.db.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DataBase {
        return Room.databaseBuilder(
            context,
            DataBase::class.java,
            "artworks_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDao(database: DataBase): Dao {
        return database.getDao()
    }
}