package com.example.camera.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.storage.data.PlantPhoto
import io.reactivex.disposables.CompositeDisposable
import kotlinx.serialization.json.Json.Default.context
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import javax.inject.Inject

class CameraViewModel @Inject constructor(viewModelContext: Context)  : ViewModel() {

    private val SaveTag = "SAVEDIMAGE"

    fun saveImage(IdNumber: Int, photoFile: File) {
        val newImage = PlantPhoto(IdNumber, "ZZ Plant", photoFile, "ZZ Plant")
        val plantFile = FileOutputStream("planbt")
        val outStream = ObjectOutputStream(plantFile)

        // Method for serialization of object
        outStream.writeObject(newImage)

        outStream.close()
        plantFile.close()
        Log.i(SaveTag, "image is saved to internal storage")
    }


}