package com.example.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.R
import com.example.presentation.databinding.ActivityArtBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtActivity: AppCompatActivity(R.layout.activity_art) {
    private val binding: ActivityArtBinding by viewBinding(ActivityArtBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = binding.artFragmentContainer.getFragment<NavHostFragment>()
        val navController = navHostFragment.navController
    }
}