package com.example.data

import com.example.data.api.Api
import com.example.domain.repository.TopRepository
import com.example.domain.models.ArtworksEntity
import retrofit2.Response

class RepositoryImpl(
    private val api: Api
): TopRepository {
    override suspend fun getArtWorks(limit:Int): Response<ArtworksEntity> = api.getArtWorks(limit)

}