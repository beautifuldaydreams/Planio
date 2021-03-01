package com.example.collection.presentation.overview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.collection.RTAG
import com.example.collection.databinding.ImageItemViewBinding
import com.example.storage.data.PlantPhoto

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */


class CollectionOverviewAdapter : ListAdapter<PlantPhoto,
        CollectionOverviewAdapter.PlantPhotoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantPhotoViewHolder {
        Log.d(RTAG, "in onCreateViewHolder")
        return PlantPhotoViewHolder(ImageItemViewBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: PlantPhotoViewHolder,
        position: Int
    ) {
        Log.d(RTAG, "in onBindViewHolder")
        val plantPhoto = getItem(position)
        holder.bind(plantPhoto)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlantPhoto>() {
        override fun areItemsTheSame(oldItem: PlantPhoto, newItem: PlantPhoto): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlantPhoto, newItem: PlantPhoto): Boolean {
            return oldItem.plantId == newItem.plantId
        }
    }

    class PlantPhotoViewHolder(private var binding:
                                 ImageItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plantPhoto: PlantPhoto) {
            Log.d(RTAG, "in bind function in PlantPhotoViewHolder")
            binding.plantPhoto = plantPhoto
            binding.executePendingBindings()
        }
    }
}