package com.example.data.api

import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import com.example.domain.models.DetailEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("v1/artworks")
    suspend fun getArtWorks(@Query("page") page: Int): Response<ArtworksEntity>

    @GET("v1/artworks/{id}")
    suspend fun getDetailArtwork(@Path("id") id: Int): Response<DetailEntity>

}