package com.example.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.domain.utils.Resource
import com.example.presentation.R
import com.example.presentation.adapters.ArtworksAdapter
import com.example.presentation.databinding.FragmentArtBinding
import com.example.presentation.databinding.FragmentDetailBinding
import com.example.presentation.viewModel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {


    private val binding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)

    private val viewModelArt: ArtViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
        observeData()

    }

    private fun observeData() {
        viewModelArt.artwork.observe(viewLifecycleOwner) {resource ->
            when (resource) {
                is Resource.Success -> {
                    val data = resource.data
                    with(binding) {
                        textTitle.text = data?.data?.artist_title
                        Glide.with(imageArt).load(data?.data?.thumbnail?.lqip).into(imageArt)
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }

                is Resource.Loading -> TODO()
            }
        }
        val artworkId = arguments?.getInt("id")
        if (artworkId != null) {
            viewModelArt.getDetailArtwork(artworkId)
        }
    }


    private fun setUpListeners() {
        binding.icnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}