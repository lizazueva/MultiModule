package com.example.domain.repository

import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import com.example.domain.models.DetailEntity
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow


interface TopRepository {
    suspend fun fetchAndCacheArtWorks(limit: Int): Resource<Boolean>

    fun getDetailArtwork(id: Int): Flow<Data>

    fun getCachedArtWorks(): Flow<ArtworksEntity>


}