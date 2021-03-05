package com.example.camera.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.camera.RTAG
import com.example.camera.databinding.PlantItemViewBinding
import com.example.storage.data.PlantIndividual

class CameraAdapter : ListAdapter<PlantIndividual,
        CameraAdapter.PlantPhotoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantPhotoViewHolder {
        Log.d(RTAG, "in camera onCreateViewHolder")
        return PlantPhotoViewHolder(
            PlantItemViewBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: PlantPhotoViewHolder,
        position: Int
    ) {
        Log.d(RTAG, "in camera onBindViewHolder")
        val plantInd = getItem(position)
        holder.bind(plantInd)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlantIndividual>() {
        override fun areItemsTheSame(oldItem: PlantIndividual, newItem: PlantIndividual): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlantIndividual, newItem: PlantIndividual): Boolean {
            return oldItem.plantId == newItem.plantId
        }
    }

    class PlantPhotoViewHolder(private var binding:
                               PlantItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plantInt: PlantIndividual) {
            Log.d(RTAG, "in camera bind function in PlantPhotoViewHolder")
            binding.plantIndividual = plantInt
            binding.executePendingBindings()
        }
    }
}