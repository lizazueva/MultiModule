package com.example.domain.usecase

import com.example.domain.repository.TopRepository
import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import com.example.domain.models.DetailEntity
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetArtWorksUseCase(
    private val repository: TopRepository
) {
    suspend operator fun invoke(limit: Int): Resource<ArtworksEntity> {
        return repository.getArtWorks(limit)
    }

    fun getCachedArtWorks(): Flow<List<Data>> {
        return repository.getCachedArtWorks()
    }
}