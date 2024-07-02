package com.example.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.presentation.R
import com.example.presentation.databinding.ActivityArtBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtActivity: AppCompatActivity() {
    private lateinit var binding: ActivityArtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.art_fragment) as NavHostFragment
        val navController = navHostFragment.navController
    }
}