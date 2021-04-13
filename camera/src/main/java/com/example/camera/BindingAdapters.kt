package com.example.camera

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

@BindingAdapter("imageFilePath")
fun bindImage(imgView: ImageView, imgUrl: File) {

    imgUrl.let {
        Glide.with(imgView.context)
            .load(findLastPlantImage(imgUrl))
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_launcher_foreground))
            .into(imgView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<PlantIndividual>?) {
    val adapter = recyclerView.adapter as CameraAdapter
    adapter.submitList(data)
}

fun findLastPlantImage(imgUrl: File): File? {

    var item: PlantPhoto? = null

    try{
        val lastPhoto = File(imgUrl.toString()).listFiles()?.last()
        val file = FileInputStream(lastPhoto)
        val inStream = ObjectInputStream(file)
        item = inStream.readObject() as PlantPhoto
    } catch (e:Exception){
        e.printStackTrace()
    }
        return item?.plantFilePath
}
