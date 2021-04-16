package com.lisaschubeka.collection.presentation.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lisaschubeka.collection.databinding.ImageItemViewBinding
import com.lisaschubeka.storage.data.PlantIndividual

class CollectionOverviewAdapter(private val onClickListener: OnClickListener) : ListAdapter<PlantIndividual,
        CollectionOverviewAdapter.PlantIndividualViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantIndividualViewHolder {
        return PlantIndividualViewHolder(ImageItemViewBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: PlantIndividualViewHolder,
        position: Int
    ) {
        val plantIndividual = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(plantIndividual)
        }
        holder.bind(plantIndividual)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlantIndividual>() {
        override fun areItemsTheSame(oldItem: PlantIndividual, newItem: PlantIndividual): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlantIndividual, newItem: PlantIndividual): Boolean {
            return oldItem.plantId == newItem.plantId
        }
    }

    class PlantIndividualViewHolder(private var binding:
                                 ImageItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(plantPhoto: PlantIndividual) {
            binding.plantIndividual = plantPhoto
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (plantIndividual: PlantIndividual) -> Unit) {
        fun onClick(plantIndividual: PlantIndividual) = clickListener(plantIndividual)
    }
}