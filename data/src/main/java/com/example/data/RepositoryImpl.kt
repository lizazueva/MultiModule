package com.example.data

import com.example.data.api.Api
import com.example.data.api.RetrofitInstance
import com.example.domain.repository.TopRepository
import com.example.domain.models.ArtworksEntity
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val retrofitInstance: RetrofitInstance
): TopRepository {
    override suspend fun getArtWorks(limit:Int): Response<ArtworksEntity> = retrofitInstance.api.getArtWorks(limit)

}