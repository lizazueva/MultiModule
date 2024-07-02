package com.example.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.models.Data
import com.example.presentation.R
import com.example.presentation.databinding.ItemArtworkBinding

class ArtworksAdapter : ListAdapter<Data, ArtworksAdapter.ViewHolder>(differCallBack) {

    var onItemClickListener: ((Data) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtworksAdapter.ViewHolder {
        val binding = ItemArtworkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtworksAdapter.ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(var binding: ItemArtworkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(deal: Data) = with(binding) {
            textTitle.text = deal.artist_title
            textDiscr.text = deal.description
            Glide.with(imageArt).load(deal.thumbnail.lqip).into(imageArt)

            itemView.setOnClickListener {
                onItemClickListener?.invoke(deal)
                val context = itemView.context
                val toastMessage = context.getString(R.string.text_toast, deal.artist_title)
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private object differCallBack : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}