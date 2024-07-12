package com.example.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Config
import com.example.domain.models.Data
import com.example.domain.models.Info
import com.example.domain.models.Pagination
import com.example.domain.usecase.GetArtWorksUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val getArtWorks: GetArtWorksUseCase
) : ViewModel() {

    private val _artworks: MutableLiveData<ArtworksEntity?> = MutableLiveData()
    val artworks: LiveData<ArtworksEntity?>
        get() = _artworks

    private var isLoading = false
    private var currentPage: Int = 1
    private var totalPages: Int = 1

    fun getArtworks(page: Int, loadTwoPages: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading = true
                if (loadTwoPages) {
                    val nextPage = page + 1
                    val firstPage = async { getArtWorks(page) }
                    val secondPage = async { getArtWorks(nextPage) }

                    val firstPageResult = firstPage.await()
                    val secondPageResult = secondPage.await()

                    val newArtworks = mutableListOf<Data>()
                    var combinedPagination: Pagination? = null
                    var combinedConfig: Config? = null
                    var combinedInfo: Info? = null

                    when (firstPageResult) {
                        is Resource.Success -> {
                            firstPageResult.data?.let {
                                newArtworks.addAll(it.data)
                                totalPages = it.pagination.total_pages
                                combinedPagination = it.pagination
                                combinedConfig = it.config
                                combinedInfo = it.info
                            } ?: Log.e("MainViewModel", "Error: Received null data")
                        }

                        is Resource.Error -> {
                            Log.e(
                                "MainViewModel",
                                "Error fetching artworks: ${firstPageResult.message}"
                            )
                        }
                    }

                    when (secondPageResult) {
                        is Resource.Success -> {
                            firstPageResult.data?.let {
                                newArtworks.addAll(it.data)
                                totalPages = it.pagination.total_pages
                                combinedPagination = it.pagination
                                combinedConfig = it.config
                                combinedInfo = it.info
                            } ?: Log.e("MainViewModel", "Error: Received null data")
                        }

                        is Resource.Error -> {
                            Log.e(
                                "MainViewModel",
                                "Error fetching artworks: ${secondPageResult.message}"
                            )
                        }
                    }

                    val newArtworksEntity = combinedPagination?.let {pagination ->
                        combinedInfo?.let { info ->
                            combinedConfig?.let { config ->
                                ArtworksEntity(
                                    config = config,
                                    data = newArtworks,
                                    info = info,
                                    pagination = pagination
                                )
                            }
                        }
                    }

                    Log.e(
                        "MainViewModel",
                        "newArtworksEntity: $newArtworksEntity"
                    )

                    _artworks.postValue(newArtworksEntity)


                } else {
                    val resource = getArtWorks(page)
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                _artworks.postValue(it)
                                totalPages = it.pagination.total_pages
                            } ?: Log.e("MainViewModel", "Error: Received null data")
                        }

                        is Resource.Error -> {
                            Log.e("MainViewModel", "Error fetching artworks: ${resource.message}")
                        }
                    }
                }
            } catch (ex: Exception) {
                Log.e(
                    ex.toString(),
                    ex.message.toString()
                )
            } finally {
                isLoading = false
            }
        }
    }

    fun onEndOfListReached() {
        if (!isLoading) {
            when {
                currentPage < totalPages - 1 -> {
                    getArtworks(currentPage, true)
                    currentPage += 2
                }
                currentPage == totalPages - 1 -> {
                    getArtworks(currentPage)
                    currentPage++
                }
            }
        }
    }
}