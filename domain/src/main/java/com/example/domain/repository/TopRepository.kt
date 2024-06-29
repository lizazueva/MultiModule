package com.example.domain.repository

import com.example.domain.models.ArtworksEntity
import retrofit2.Response

interface TopRepository {

    suspend fun getArtWorks(limit: Int): Response<ArtworksEntity>
}