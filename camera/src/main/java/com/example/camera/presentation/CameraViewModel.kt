package com.example.camera.presentation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.storage.data.PlantPhoto
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class CameraViewModel(application: Application)  : AndroidViewModel(application) {

    private val SaveTag = "SAVEDIMAGE"
    private val context = getApplication<Application>().applicationContext

    lateinit var spIdNumber : String

    init {
        if (!this::spIdNumber.isInitialized) {
            spIdNumber = "0"
        }
    }

    fun editSpIdNumber() {
        val sharedPreferences : SharedPreferences = context.getSharedPreferences(
            "spIdNumber",
            Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        var num = sharedPreferences.getString("spIdNumber", "0")?.toInt()
        if (num != null) {
            num++
        }
        editor.putString("spIdNumber", num.toString())
        editor.apply()
    }

    fun getNewSpIdNumber() : String? {
        val sharedPreferences : SharedPreferences = context.getSharedPreferences(
            "spIdNumber",
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString("spIdNumber", "0")
    }

    fun saveImage(IdNumber: Int, photoFile: File, plantType: Int) {

        val newImage = PlantPhoto(IdNumber, "ZZ Plant", photoFile, plantType)

        val dir = File(
            context.getExternalFilesDir(null), "planio/dataclasses"
        )
        if (!dir.exists()) {
            dir.mkdirs()
            Log.i(SaveTag, dir.absolutePath)
        }
        Log.i(SaveTag, dir.absolutePath)
        Log.i(SaveTag, "${dir.exists()}")

        val dirOne = File(dir, "$plantType")

        if(!dirOne.exists()){
            dir.mkdirs()
            Log.i(SaveTag, "After dirOne made: $dirOne.absolutePath")
        }

        Log.i(SaveTag, dirOne.absolutePath)
        Log.i(SaveTag, dirOne.isFile.toString())
        Log.i(SaveTag, dirOne.isDirectory.toString())

        val dataClassLocation = File(dirOne, "$IdNumber")

        if (dataClassLocation.exists()) {
            dataClassLocation.delete()
            dataClassLocation.createNewFile()
        } else{
            dataClassLocation.createNewFile()
        }


        val plantFile = FileOutputStream(dataClassLocation, true)
        val outStream = ObjectOutputStream(plantFile)

        // Method for serialization of object
        outStream.writeObject(newImage)
        outStream.close()
        plantFile.close()
        Log.i(SaveTag, "Image saved successfully")
    }
}
