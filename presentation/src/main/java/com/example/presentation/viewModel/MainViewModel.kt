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
class MainViewModel @Inject constructor(
    private val getArtWorks: GetArtWorksUseCase
) : ViewModel() {

    private val _artworks: MutableLiveData<ArtworksEntity?> = MutableLiveData()
    val artworks: LiveData<ArtworksEntity?>
        get() = _artworks

    fun getArtworks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resource = getArtWorks(2)
                when (resource) {
                    is Resource.Success -> _artworks.postValue(resource.data)
                    is Resource.Error -> {
                        Log.e("MainViewModel", "Error fetching artworks: ${resource.message}")
                    }
                }
            } catch (ex: Exception) {
                Log.e(
                    ex.toString(),
                    ex.message.toString()
                )
            }

        }
    }

}