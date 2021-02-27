package com.example.collection.presentation.overview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.storage.data.PlantPhoto
import java.io.*
import java.util.*

class CollectionOverviewViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var mediaLists: MutableList<File>
    private val context = getApplication<Application>().applicationContext

    fun locateImgInExternal(): File {
        val dir = File(context.getExternalFilesDir("TestFolder").toString())
        val file = File(dir, "/TestFolder")
        Log.i("OnCreate", file.toString())
        return file
    }

    fun retrieveFileList() {
        //Todo: Query plant data and file paths through dataclasses and not directly through file paths
        mediaLists = context?.getExternalFilesDir("planio/dataclasses")
            ?.listFiles()?.toMutableList() ?: mutableListOf()
        Log.i("OnCreate", "mediaList created")
        Log.i("OnCreate", "File path: " + context?.getExternalFilesDir("planio/dataclasses").toString())
        Log.i("OnCreate", "mediaLists size: " + mediaLists.size.toString())
        Log.i("OnCreate", "mediaLists size: ")

        for (item in mediaLists) {
            Log.i("OnCreate", "${item.absolutePath}")
        }
    }

    fun getImgFromPlantIndividual(dataclassFile : File): File {
        val file = FileInputStream(dataclassFile)
        val inStream = ObjectInputStream(file)

        // Method for deserialization of object
        val item = inStream.readObject() as PlantPhoto

        inStream.close()
        file.close()
        return item.plantFilePath
    }

//    mediaLists = context?.getExternalFilesDir("planio/dataclasses")?.listFiles { file ->
//        EXTENSION_WHITELIST.contains(file.extension.toUpperCase(Locale.ROOT))
//    }?.sortedDescending()?.toMutableList() ?: mutableListOf()
}