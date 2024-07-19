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

    fun getSinglePageArtworks(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading = true
                val resource = getArtWorks(page)
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            _artworks.postValue(it)
                            totalPages = it.pagination.total_pages
                        } ?: Log.e("MainViewModel", "Error: Received null data")
                    }

                    is Resource.Error -> {
                        Log.e(
                            "MainViewModel",
                            "Error fetching artworks: ${resource.message}"
                        )
                    }
                }
            } catch (ex: Exception) {
                Log.e(ex.toString(), ex.message.toString())
            } finally {
                isLoading = false
            }
        }
    }

    private fun getTwoPagesArtworks(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading = true
                val nextPage = page + 1
                val firstPage = async { getArtWorks(page) }
                val secondPage = async { getArtWorks(nextPage) }

                val firstPageResult = firstPage.await()
                val secondPageResult = secondPage.await()

                val newArtworks = mutableListOf<Data>()
                var pagination: Pagination? = null

                when (firstPageResult) {
                    is Resource.Success -> {
                        firstPageResult.data?.let {
                            newArtworks.addAll(it.data)
                            totalPages = it.pagination.total_pages
                            pagination = it.pagination
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
                        secondPageResult.data?.let {
                            newArtworks.addAll(it.data)
                            totalPages = it.pagination.total_pages
                            pagination = it.pagination
                        } ?: Log.e("MainViewModel", "Error: Received null data")
                    }

                    is Resource.Error -> {
                        Log.e(
                            "MainViewModel",
                            "Error fetching artworks: ${secondPageResult.message}"
                        )
                    }
                }

                val newArtworksEntity = pagination?.let {
                    ArtworksEntity(data = newArtworks, pagination = it)
                }

                Log.e("MainViewModel", "newArtworksEntity: $newArtworksEntity")

                _artworks.postValue(newArtworksEntity)
            } catch (ex: Exception) {
                Log.e(ex.toString(), ex.message.toString())
            } finally {
                isLoading = false
            }
        }
    }

    fun onEndOfListReached() {
        if (!isLoading) {
            when {
                currentPage < totalPages - 1 -> {
                    getTwoPagesArtworks(currentPage + 1)
                    currentPage += 2
                }

                currentPage == totalPages - 1 -> {
                    getSinglePageArtworks(++currentPage)
                }
            }
        }
    }
}