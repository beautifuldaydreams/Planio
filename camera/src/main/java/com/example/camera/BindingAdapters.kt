package com.example.camera

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.camera.presentation.CameraAdapter
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
    val adapter = recyclerView.adapter as CameraAdapter
    adapter.submitList(data)
    Log.d(RTAG, "executed camera bindRecyclerView")
}

fun findLastPlantImage(imgUrl: File): PlantPhoto? {

    var item: PlantPhoto? = null

    Log.i(RTAG, "in findLastPlantImage")
    //Todo: get photo if not found load empty photo
    //Todo: query list by descending to find latest photo
    val lastPhoto = File(imgUrl, "1")
    Log.i(RTAG, "last photo path: ${lastPhoto.absolutePath}")
    Log.i(RTAG, "last photo file: ${lastPhoto.absoluteFile}")
    try{
        val file = FileInputStream(lastPhoto)
        val inStream = ObjectInputStream(file)
        item = inStream.readObject() as PlantPhoto
    } catch (e:Exception){
        e.printStackTrace()
    }
    return item
}
