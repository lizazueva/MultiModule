package com.example.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.adapters.ArtworksAdapter
import com.example.presentation.databinding.FragmentArtBinding
import com.example.presentation.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtFragment : Fragment() {


    private var _binding: FragmentArtBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterArt: ArtworksAdapter
    private val viewModel: MainViewModel by viewModels()
    private var isLastPage = false
    private var isLoading = false

    private var currentPage = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        setData()
        setScrollListener()


    }

    private fun setData() {
        viewModel.artworks.observe(viewLifecycleOwner) {
            val newData = it?.data
            if (newData != null) {
                if (currentPage == 1) {
                    adapterArt.submitList(newData)
                } else {
                    adapterArt.submitList(adapterArt.currentList + newData)
                }
                isLastPage = newData.isEmpty()
                isLoading = false
            }

        }
        viewModel.getArtworks(currentPage)
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

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        loadMoreItems()
                    }
                }
            }
        })
    }

    private fun loadMoreItems() {
        isLoading = true
        currentPage++
        viewModel.getArtworks(currentPage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}