package com.example.multimodule.di

import com.example.domain.repository.TopRepository
import com.example.domain.usecase.GetArtWorksUseCase
import com.example.domain.usecase.GetDetailArtworkUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetArtWorksUseCase(repository: TopRepository): GetArtWorksUseCase {
        return GetArtWorksUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGetDetailArtworkUseCase(repository: TopRepository): GetDetailArtworkUseCase {
        return GetDetailArtworkUseCase(repository)
    }

}