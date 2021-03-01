package com.example.collection

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.collection.presentation.overview.CollectionOverviewAdapter
import com.example.storage.data.PlantPhoto
import java.io.File

const val RTAG = "RECYCLERVIEW"

@BindingAdapter("imageFilePath")
fun bindImage(imgView: ImageView, imgUrl: File) {
    Log.d(RTAG, "in bindImage in BindingAdapters")
    imgUrl.let {
        Glide.with(imgView.context)
            .load(imgUrl)
                //Todo: create loading animation and failure to load animation corresponding to UI
//            .apply(
//                RequestOptions()
//                .placeholder(R.drawable.loading_animation)
//                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
    Log.d(RTAG, "executed bindImage")
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<PlantPhoto>?) {
    val adapter = recyclerView.adapter as CollectionOverviewAdapter
    adapter.submitList(data)
    Log.d(RTAG, "executed bindRecyclerView")
}
