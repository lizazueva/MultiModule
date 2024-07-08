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

    private val _totalPages: MutableLiveData<Int> = MutableLiveData()
    val totalPages: LiveData<Int>
        get() = _totalPages

    private val _currentPage: MutableLiveData<Int> = MutableLiveData(1)
    val currentPage: LiveData<Int>
        get() = _currentPage

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun getArtworks(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _isLoading.postValue(true)
                val resource = getArtWorks(page)
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            _artworks.postValue(it)
                            _totalPages.postValue(it.pagination.total_pages)
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
                _isLoading.postValue(false)
            }
        }
    }

    fun loadMoreItems() {
        val current = _currentPage.value ?: 1
        if (current < (_totalPages.value ?: 1) && _isLoading.value == false) {
            _currentPage.postValue(current + 1)
            getArtworks(current + 1)
        }
    }
}