package com.lisasb.collection.presentation.individual

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lisasb.collection.databinding.ImagePlantPhotoViewBinding
import com.lisasb.storage.data.PlantPhoto

class CollectionIndividualAdapter(private val onClickListener: OnClickListener) : ListAdapter<PlantPhoto,
        CollectionIndividualAdapter.PlantPhotoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantPhotoViewHolder {
        return PlantPhotoViewHolder(
            ImagePlantPhotoViewBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: PlantPhotoViewHolder,
        position: Int
    ) {
        val plantPhoto = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(plantPhoto)
        }
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
                                    ImagePlantPhotoViewBinding
    ):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plantPhoto: PlantPhoto) {
            binding.plantPhoto = plantPhoto
        }
    }

    class OnClickListener(val clickListener: (plantPhoto:PlantPhoto) -> Unit) {
        fun onClick(plantPhoto: PlantPhoto) = clickListener(plantPhoto)
    }
}

