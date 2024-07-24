package com.example.domain.repository

import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import com.example.domain.models.DetailEntity
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow


interface TopRepository {
    suspend fun getArtWorks(limit: Int): Resource<ArtworksEntity>

    suspend fun getDetailArtwork(id: Int): Resource<DetailEntity>

    fun getCachedArtWorks(): Flow<List<Data>>


}