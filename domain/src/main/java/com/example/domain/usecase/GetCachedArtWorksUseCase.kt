package com.example.domain.usecase

import com.example.domain.repository.TopRepository

class GetCachedArtWorksUseCase constructor(
    private val repository: TopRepository
) {
    operator fun invoke() = repository.getCachedArtWorks()
}