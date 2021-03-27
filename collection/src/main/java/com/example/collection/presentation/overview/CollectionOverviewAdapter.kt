package com.example.collection.presentation.overview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.collection.RTAG
import com.example.collection.databinding.ImageItemViewBinding
import com.example.collection.presentation.individual.CollectionIndividualAdapter
import com.example.storage.data.PlantIndividual
import com.example.storage.data.PlantPhoto

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
val debug1 = "DEBUG1"

class CollectionOverviewAdapter(private val onClickListener: OnClickListener) : ListAdapter<PlantIndividual,
        CollectionOverviewAdapter.PlantIndividualViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantIndividualViewHolder {
        Log.d(RTAG, "in onCreateViewHolder")
        return PlantIndividualViewHolder(ImageItemViewBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: PlantIndividualViewHolder,
        position: Int
    ) {
        Log.d(RTAG, "in onBindViewHolder")
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
            Log.d(debug1, "plantindividual photo: ${plantPhoto.plantFilePath}")
            binding.plantIndividual = plantPhoto
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (plantIndividual:PlantIndividual) -> Unit) {
        fun onClick(plantIndividual: PlantIndividual) = clickListener(plantIndividual)
    }

//    fun submitList2(newData: MutableList<PlantIndividual>) {
//        myData.clear()
//        myData.addAll(newData)
//        notifyDataSetChanged()
//    }
}