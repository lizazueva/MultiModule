package com.example.domain.repository

import com.example.domain.models.ArtworksEntity
import com.example.domain.utils.Resource

interface TopRepository {
    suspend fun getArtWorks(limit: Int): Resource<ArtworksEntity>
}