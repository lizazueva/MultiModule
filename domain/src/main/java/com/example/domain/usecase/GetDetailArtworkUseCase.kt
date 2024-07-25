package com.example.domain.usecase

import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import com.example.domain.models.DetailEntity
import com.example.domain.repository.TopRepository
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class GetDetailArtworkUseCase (
    private val repository: TopRepository
) {
    operator fun invoke(id: Int): Flow<Data> {
        return repository.getDetailArtwork(id)
    }
}