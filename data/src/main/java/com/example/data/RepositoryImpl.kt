package com.example.data

import com.example.data.api.Api
import com.example.data.db.Dao
import com.example.domain.repository.TopRepository
import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import com.example.domain.models.DetailEntity
import com.example.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: Api,
    private val dao: Dao
) : TopRepository {
    override suspend fun getArtWorks(limit: Int): Resource<ArtworksEntity> {
        val resp = api.getArtWorks(limit)
        return try {
            if (resp.isSuccessful) {
                val body = resp.body()
                if (body != null) {
                    dao.insertArtworks(body)
                    dao.insertData(body.data)
                    Resource.Success(body)
                } else {
                    Resource.Error("Ошибка при получении списка")
                }
            } else {
                val errorBody = resp.errorBody()?.toString()
                Resource.Error(errorBody ?: "Ошибка при получении списка")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Неизвестная ошибка")
        }
    }

    override suspend fun getDetailArtwork(id: Int): Resource<DetailEntity> {
        val resp = api.getDetailArtwork(id)
        return try {
            if (resp.isSuccessful) {
                val body = resp.body()
                if (body != null) {
                    Resource.Success(body)
                } else {
                    Resource.Error("Ошибка при получении детальной информации")
                }
            } else {
                val errorBody = resp.errorBody()?.string()
                when (resp.code()) {
                    500 -> Resource.Error("Сервер не работает")
                    else -> Resource.Error(errorBody ?: "Ошибка при получении детальной информации")
                }
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Неизвестная ошибка")
        }
    }

    override fun getCachedArtWorks(): Flow<List<Data>> {
        return dao.getAllArtworks()
    }
}