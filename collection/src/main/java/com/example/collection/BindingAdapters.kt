package com.example.collection

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.collection.presentation.individual.CollectionIndividualAdapter
import com.example.collection.presentation.overview.CollectionOverviewAdapter
import com.example.storage.data.PlantIndividual
import com.example.storage.data.PlantPhoto
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.lang.Exception

const val RTAG = "RECYCLERVIEW"

@BindingAdapter("imageFilePath")
fun bindImage(imgView: ImageView, imgUrl: File) {
    Log.d(RTAG, "in bindImage in BindingAdapters")
    imgUrl.let {
        Glide.with(imgView.context)
            .load(findLastPlantImage(imgUrl))
                //Todo: create loading animation and failure to load animation corresponding to UI
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
    Log.d(RTAG, "executed bindImage")
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<PlantIndividual>?) {
    val adapter = recyclerView.adapter as CollectionOverviewAdapter
    adapter.submitList(data)
    Log.d("DEBUG1", "overview data not null? " + data?.isEmpty())
    Log.d(RTAG, "executed bindRecyclerView")
}

fun findLastPlantImage(imgUrl: File): File? {

    var item: PlantPhoto? = null

    Log.i(RTAG, "in findLastPlantImage")
    //Todo: get photo if not found load empty photo
    //Todo: query list by descending to find latest photo

    try{
        val lastPhoto = File(imgUrl.toString()).listFiles()?.last()
        val file = FileInputStream(lastPhoto)
        val inStream = ObjectInputStream(file)
        item = inStream.readObject() as PlantPhoto
    } catch (e: Exception){
        e.printStackTrace()
    }
    return item?.plantFilePath
}

@BindingAdapter("imagePhotoFilePath")
fun bindImagePhoto(imgView: ImageView, imgUrl: File) {
    Log.d("DEBUG1", "in bindPhotoImage in BindingAdapters")
    imgUrl.let {
        Glide.with(imgView.context)
            .load(imgUrl)
            //Todo: create loading animation and failure to load animation corresponding to UI
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
    Log.d(RTAG, "executed bindImagePhoto")
}

@BindingAdapter("listPhotoData")
fun bindPlantRecyclerView(recyclerView: RecyclerView,
                     data: List<PlantPhoto>?) {
    val adapter = recyclerView.adapter as CollectionIndividualAdapter
    adapter.submitList(data)
    Log.d("DEBUG1", "is data empty " + data?.isEmpty())
    Log.d("DEBUG1", "is data empty " + data?.isNullOrEmpty())
    Log.d("DEBUG1", "executed bindRecyclerView")
}

@BindingAdapter("singleImage")
fun loadImage(imgView: ImageView, imgUrl: File?) {

    imgUrl.let {
        Glide.with(imgView.context)
            .load(imgUrl)
            //Todo: create loading animation and failure to load animation corresponding to UI
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

