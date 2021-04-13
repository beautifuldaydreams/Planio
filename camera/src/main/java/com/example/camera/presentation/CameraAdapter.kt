package com.example.camera.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.camera.databinding.PlantItemViewBinding
import com.example.storage.data.PlantIndividual

class CameraAdapter(private val onClickListener: OnClickListener) : ListAdapter<PlantIndividual,
        CameraAdapter.PlantPhotoViewHolder>(DiffCallback) {

    init {
        setHasStableIds(true)
    }
    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantPhotoViewHolder {
        return PlantPhotoViewHolder(
            PlantItemViewBinding.inflate(
                LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: PlantPhotoViewHolder,
        position: Int
    ) {
        val plantId = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(plantId)
        }
            holder.bind(plantId)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlantIndividual>() {
        override fun areItemsTheSame(oldItem: PlantIndividual, newItem: PlantIndividual): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlantIndividual, newItem: PlantIndividual): Boolean {
            return oldItem.plantId == newItem.plantId
        }
    }

    inner class PlantPhotoViewHolder(private var binding:
                                     PlantItemViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(plantInt: PlantIndividual) = with(itemView) {
            binding.plantIndividual = plantInt
            binding.executePendingBindings()
        }

    }

    class OnClickListener(val clickListener: (plantIndividual:PlantIndividual) -> Unit) {
        fun onClick(plantIndividual: PlantIndividual) = clickListener(plantIndividual)
    }
}