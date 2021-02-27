package com.example.camera.presentation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.provider.Settings.System.getString
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.camera.MyApplication
import com.example.camera.R
import com.example.storage.data.PlantPhoto
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import javax.inject.Inject
import kotlin.properties.Delegates

class CameraViewModel (application: Application)  : AndroidViewModel(application) {

    private val SaveTag = "SAVEDIMAGE"
    private val context = getApplication<Application>().applicationContext

    lateinit var spIdNumber : String

    init {
        if (!this::spIdNumber.isInitialized) {
            spIdNumber = "0"
        }
    }

    fun editSpIdNumber() {
        val sharedPreferences : SharedPreferences = context.getSharedPreferences("spIdNumber", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        var num = sharedPreferences.getString("spIdNumber", "0")?.toInt()
        if (num != null) {
            num++
        }
        editor.putString("spIdNumber",num.toString())
        editor.apply()
    }

    fun getNewSpIdNumber() : String? {
        val sharedPreferences : SharedPreferences = context.getSharedPreferences("spIdNumber", Context.MODE_PRIVATE)
        return sharedPreferences.getString("spIdNumber", "0")
    }

    fun saveImage(IdNumber: Int, photoFile: File) {
        try {
            val newImage = PlantPhoto(IdNumber, "ZZ Plant", photoFile, "ZZ Plant")

            val dir = File(context.getExternalFilesDir("planio/dataclasses").toString())
            if (!dir.exists()) {
                Log.i(SaveTag, "making Planio/dataclasses File")
                dir.mkdirs()
            }

            val dataClassLocation = File(dir, "$IdNumber")
            if (dataClassLocation.exists()) {
                dataClassLocation.delete()
                Log.i(SaveTag, "making random File")
                dataClassLocation.createNewFile()
            } else{
                dataClassLocation.createNewFile()
            }

            Log.i(SaveTag, "FILE PATH: $dataClassLocation")
            val plantFile = FileOutputStream(dataClassLocation, true)
            Log.i(SaveTag, "FILE PATH(plantFile): $plantFile")
            val outStream = ObjectOutputStream(plantFile)

            Log.i(SaveTag, "FILE PATH(outStream): $outStream")
            // Method for serialization of object
            outStream.writeObject(newImage)
            outStream.close()
            plantFile.close()
            Log.i(SaveTag, "Image saved successfully")
        } catch (e: Exception) {
            Log.i(SaveTag, "YIKES")
            e.printStackTrace()
        }
    }
}