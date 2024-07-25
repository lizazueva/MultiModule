package com.example.data

import android.util.Log
import com.example.data.api.Api
import com.example.data.db.Dao
import com.example.domain.repository.TopRepository
import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api,
    private val dao: Dao
) : TopRepository {
    override suspend fun fetchAndCacheArtWorks(limit: Int): Resource<Boolean> {
        val resp = api.getArtWorks(limit)
        return try {
            if (resp.isSuccessful) {
                val body = resp.body()
                if (body != null) {
                    dao.insertArtworks(body)
                    dao.insertData(body.data)
                    Resource.Success(true)
                } else {
                    Resource.Error("Ошибка при получении списка")
                }
            } else {
                val errorBody = resp.errorBody()?.string()
                when (resp.code()) {
                    500 -> Resource.Error("Сервер не работает")
                    else -> Resource.Error(errorBody ?: "Ошибка при получении списка")
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Неизвестная ошибка")
        }
    }

    override fun getDetailArtwork(id: Int): Flow<Data> {
        return dao.getDetailArtwork(id).onEach { data ->
            Log.d("ArtRepository", "Fetched artwork detail: $data")
        }
    }

    override fun getCachedArtWorks(): Flow<ArtworksEntity> {
        return dao.getAllArtworks()
    }
}