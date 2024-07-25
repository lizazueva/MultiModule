package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtworks(artworks: ArtworksEntity)

    @Query("SELECT*FROM artworks")
    fun getAllArtworks(): Flow<ArtworksEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: List<Data>)

    @Query("SELECT * FROM data WHERE id = :arg0")
    fun getDetailArtwork(arg0: Int): Flow<Data>

}