package com.example.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ArtworksEntity
import com.example.domain.usecase.GetArtWorksUseCase
import com.example.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    fun getArtworks(page: Int) {
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
                        Log.e("MainViewModel", "Error fetching artworks: ${resource.message}")
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
        if (currentPage < totalPages && !isLoading) {
            currentPage++
            getArtworks(currentPage)
        }
    }
}