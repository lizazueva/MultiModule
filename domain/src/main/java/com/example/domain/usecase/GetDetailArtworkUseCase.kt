package com.example.domain.usecase

import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import com.example.domain.models.DetailEntity
import com.example.domain.repository.TopRepository
import com.example.domain.utils.Resource

class GetDetailArtworkUseCase (
    private val repository: TopRepository
) {
    suspend operator fun invoke(id: Int): Resource<DetailEntity> {
        return repository.getDetailArtwork(id)
    }
}