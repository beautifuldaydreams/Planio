package com.example.camera.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.camera.RTAG
import com.example.camera.databinding.PlantItemViewBinding
import com.example.storage.data.PlantIndividual
import kotlinx.android.synthetic.main.plant_item_view.view.*

class CameraAdapter : ListAdapter<PlantIndividual,
        CameraAdapter.PlantPhotoViewHolder>(DiffCallback) {

    var tracker: SelectionTracker<String>? = null

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    class MyItemKeyProvider(private val adapter: CameraAdapter) : ItemKeyProvider<String>(SCOPE_CACHED)
    {
        override fun getKey(position: Int): String? =
            adapter.currentList[position].plantId.toString()
        override fun getPosition(key: String): Int =
            adapter.currentList.indexOfFirst {it.plantId.toString() == key}
    }

    class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<String>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<String>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as PlantPhotoViewHolder).getItemDetails()
            }
            return null
        }
    }

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
        val plantId = getItem(position)
        tracker?.let {
            holder.bind(plantId, it.isSelected(position.toString()))
        }
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
        fun bind(plantInt: PlantIndividual, selected: Boolean = false) = with(itemView) {
            binding.plantIndividual = plantInt
            plantSelectedView.isVisible = selected
            binding.executePendingBindings()
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
            object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): String? = getItem(adapterPosition).plantId.toString()
                override fun inSelectionHotspot(e: MotionEvent): Boolean {
                    return true
                }
            }
    }
}