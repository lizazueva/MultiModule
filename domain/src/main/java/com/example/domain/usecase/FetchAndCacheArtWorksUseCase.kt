package com.example.domain.usecase

import com.example.domain.repository.TopRepository
import com.example.domain.models.ArtworksEntity
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class FetchAndCacheArtWorksUseCase(
    private val repository: TopRepository
) {
    suspend operator fun invoke(limit: Int): Resource<Boolean> {
        return repository.fetchAndCacheArtWorks(limit)
    }
}