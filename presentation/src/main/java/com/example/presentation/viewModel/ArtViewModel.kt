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
import com.example.domain.usecase.GetArtWorksUseCase
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
    private val getArtWorks: GetArtWorksUseCase,
    private val getDetailArtworkUseCase: GetDetailArtworkUseCase
) : ViewModel() {

    private val _artworks = MutableStateFlow<Resource<List<Data>>>(Resource.Loading())
    val artworks: MutableStateFlow<Resource<List<Data>>> = _artworks

    private val _artwork: MutableLiveData<Resource<DetailEntity>> = MutableLiveData()
    val artwork: LiveData<Resource<DetailEntity>>
        get() = _artwork

    private var isLoading = false
    private var currentPage: Int = 1
    private var totalPages: Int = 1

    init {
        fetchCachedArtworks()
    }

    private fun fetchCachedArtworks() {
        viewModelScope.launch {
            getArtWorks.getCachedArtWorks().collect { data ->
                _artworks.value = Resource.Success(data)
            }
        }
    }

    fun getDetailArtwork(id:Int){
        viewModelScope.launch {
            try {
                val resource = getDetailArtworkUseCase(id)
                _artwork.postValue(resource)
            } catch (ex: Exception) {
                _artwork.postValue(Resource.Error(ex.message ?: "Неизвестная ошибка"))
            }
        }
    }

    fun getSinglePageArtworks(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                isLoading = true
                val resource = getArtWorks(page)
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let {
//                            _artworks.value = it
                            totalPages = it.pagination.total_pages
                        } ?: Log.e("MainViewModel", "Error: Received null data")
                    }

                    is Resource.Error -> {
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

                    is Resource.Loading -> TODO()
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

                    is Resource.Loading -> TODO()
                }

                val newArtworksEntity = pagination?.let {
                    ArtworksEntity(data = newArtworks, pagination = it)
                }

                Log.e("MainViewModel", "newArtworksEntity: $newArtworksEntity")

//                _artworks.postValue(newArtworksEntity)
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