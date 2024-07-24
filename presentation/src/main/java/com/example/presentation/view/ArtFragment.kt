package com.example.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.domain.utils.Resource
import com.example.presentation.R
import com.example.presentation.adapters.ArtworksAdapter
import com.example.presentation.databinding.FragmentArtBinding
import com.example.presentation.viewModel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtFragment : Fragment(R.layout.fragment_art) {


    private val binding: FragmentArtBinding by viewBinding(FragmentArtBinding::bind)
    private val adapterArt: ArtworksAdapter by lazy {
        ArtworksAdapter()
    }
    private val viewModelArt: ArtViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        observeData()
        setScrollListener()
        setUpListeners()

    }

    private fun setUpListeners() {
        adapterArt.onItemClickListener = { data ->
            val action = ArtFragmentDirections.actionArtFragmentToDetailFragment(data.id)
            findNavController().navigate(action)
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModelArt.artworks.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        resource.data?.let { artworkEntity ->
                            adapterArt.submitList(adapterArt.currentList.plus(artworkEntity))
                        }
                    }
                    is Resource.Error -> {
                        Log.e("ArtFragment", "Error: ${resource.message}")
                    }
                }
            }
        }

        viewModelArt.getSinglePageArtworks(1)
    }

    private fun setUpAdapter() {
        binding.recyclerArtworks.adapter = adapterArt
        binding.recyclerArtworks.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setScrollListener() {
        binding.recyclerArtworks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModelArt.onEndOfListReached()
                }
            }
        })
    }
}