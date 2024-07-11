package com.example.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.R
import com.example.presentation.adapters.ArtworksAdapter
import com.example.presentation.databinding.FragmentArtBinding
import com.example.presentation.viewModel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtFragment : Fragment(R.layout.fragment_art) {


    private val binding: FragmentArtBinding by viewBinding(FragmentArtBinding::bind)
    private var adapterArt: ArtworksAdapter? = null
    private val viewModelArt: ArtViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        observeData()
        setScrollListener()
    }

    private fun observeData() {
        viewModelArt.artworks.observe(viewLifecycleOwner) {artworkEntity ->
            artworkEntity?.let {
                adapterArt?.submitList(adapterArt?.currentList?.plus(it.data))
            }
        }

        viewModelArt.getArtworks(1)
    }

    private fun setUpAdapter() {
        adapterArt = ArtworksAdapter()
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