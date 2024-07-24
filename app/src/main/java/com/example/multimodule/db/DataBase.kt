package com.example.multimodule.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.db.Dao
import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import com.example.domain.models.DetailEntity
import com.example.domain.models.Pagination
import com.example.domain.models.Thumbnail

@Database(
    entities = [ArtworksEntity::class, Data::class, DetailEntity::class, Pagination::class, Thumbnail::class],
    version = 3
)
@TypeConverters(Converters::class)
abstract class DataBase : RoomDatabase() {
    abstract fun getDao(): Dao
}
