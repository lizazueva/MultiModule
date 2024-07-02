package com.example.domain.usecase

import com.example.domain.repository.TopRepository
import com.example.domain.models.ArtworksEntity
import com.example.domain.utils.Resource

class GetArtWorksUseCase(
    private val repository: TopRepository
) {
    suspend operator fun invoke(limit: Int): Resource<ArtworksEntity> {
        return repository.getArtWorks(limit)
    }
}