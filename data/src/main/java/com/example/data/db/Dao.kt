package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import com.example.domain.models.DetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtworks(artworks: ArtworksEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<Data>)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertDetailEntity(detail: DetailEntity)

    @Query("SELECT*FROM data")
    fun getAllArtworks(): Flow<List<Data>>

}