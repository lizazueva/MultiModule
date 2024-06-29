package com.example.data.api

import com.example.domain.models.ArtworksEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("v1/artworks")
    suspend fun getArtWorks(@Query("query") limit: Int): Response<ArtworksEntity>

}