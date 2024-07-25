package com.example.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ArtworksEntity
import com.example.domain.models.Data
import com.example.domain.models.DetailEntity
import com.example.domain.models.Pagination
import com.example.domain.usecase.FetchAndCacheArtWorksUseCase
import com.example.domain.usecase.GetCachedArtWorksUseCase
import com.example.domain.usecase.GetDetailArtworkUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(
    private val fetchAndCacheArtWorks: FetchAndCacheArtWorksUseCase,
    private val getDetailArtworkUseCase: GetDetailArtworkUseCase,
    private val getCachedArtWorksUseCase: GetCachedArtWorksUseCase
) : ViewModel() {

    private val _artworks = MutableStateFlow<Resource<ArtworksEntity>>(Resource.Loading())
    val artworks: StateFlow<Resource<ArtworksEntity>> = _artworks

    private val _artwork = MutableStateFlow<Data?>(null)
    val artwork: StateFlow<Data?> = _artwork

    private var isLoading = false
    private var currentPage: Int = 1
    private var totalPages: Int = 1

    init {
        fetchCachedArtworks()
    }

    private fun fetchCachedArtworks() {
        viewModelScope.launch {
            getCachedArtWorksUseCase.invoke().collect() { data ->
                _artworks.value = Resource.Success(data)
                totalPages = data?.pagination?.total_pages?: totalPages
            }
        }
    }

    fun getDetailArtwork(id: Int) {
        viewModelScope.launch {
            getDetailArtworkUseCase(id).collect { resource ->
                _artwork.value = resource
            }
        }
    }

    fun getSinglePageArtworks(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading = true
                val resource = fetchAndCacheArtWorks(page)
                when (resource) {
                    is Resource.Success -> {
                        fetchCachedArtworks()
                    }

                    is Resource.Error -> {
                        _artworks.value = Resource.Error(resource.message?:"Неизвестная ошибка")

                        Log.e(
                            "MainViewModel",
                            "Error fetching artworks: ${resource.message}"
                        )
                    }

                    is Resource.Loading -> TODO()
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
                val firstPage = async { fetchAndCacheArtWorks(page) }
                val secondPage = async { fetchAndCacheArtWorks(nextPage) }

                val firstPageResult = firstPage.await()
                val secondPageResult = secondPage.await()

                if (firstPageResult is Resource.Success || secondPageResult is Resource.Success) {
                    fetchCachedArtworks()
                }

                if (firstPageResult is Resource.Error) {
                    _artworks.value = Resource.Error(firstPageResult.message?:"Неизвестная ошибка")
                }

                if (secondPageResult is Resource.Error) {
                    _artworks.value = Resource.Error(secondPageResult.message?:"Неизвестная ошибка")
                }
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