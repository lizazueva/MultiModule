package com.example.multimodule.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.db.Dao
import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data

@Database(
    entities = [ArtworksEntity::class, Data::class],
    version = 8
)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {
    abstract fun getDao(): Dao
}
