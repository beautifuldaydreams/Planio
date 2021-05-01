package com.lisaschubeka.camera

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lisaschubeka.camera.R
import com.lisaschubeka.camera.presentation.CameraAdapter
import com.lisaschubeka.storage.SharedPreferences
import com.lisaschubeka.storage.data.PlantIndividual
import com.lisaschubeka.storage.data.PlantPhoto
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

@BindingAdapter("textPlant")
fun loadText(textView: TextView, plantIndividual: PlantIndividual){
    textView.text = SharedPreferences.getNewSpIdNumber(
        plantIndividual.plantId.toString() + "a",
        textView.context,
        plantIndividual.plantName
    )
}
