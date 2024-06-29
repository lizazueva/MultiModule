package com.example.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetArtWorksUseCase

class MainViewModel(
    private val getArtWorks: GetArtWorksUseCase
): ViewModel() {

}