package com.example.domain.usecase

import com.example.domain.repository.TopRepository
import com.example.domain.models.ArtworksEntity
import com.example.domain.utils.Resource

class GetArtWorksUseCase (
    private val repository: TopRepository
) {
    suspend operator fun invoke(limit: Int): Resource<ArtworksEntity?> {
        val resp = repository.getArtWorks(limit)
        if (resp.isSuccessful){
            val body = resp.body()
            return Resource.Success(body)
        } else {
            val errorBody = resp.errorBody()?.toString()
            return Resource.Error(errorBody ?: "Ошибка при получении списка")
        }
    }
}