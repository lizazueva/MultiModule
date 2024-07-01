package com.example.multimodule.di

import com.example.data.RepositoryImpl
import com.example.data.api.RetrofitInstance
import com.example.domain.repository.TopRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun provideRepositoryImpl(retrofitInstance: RetrofitInstance): TopRepository {
        return RepositoryImpl(retrofitInstance)
    }
}