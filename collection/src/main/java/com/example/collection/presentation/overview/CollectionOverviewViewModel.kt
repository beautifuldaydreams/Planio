package com.example.collection.presentation.overview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.storage.data.PlantPhoto
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

class CollectionOverviewViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var mediaLists: MutableList<File>
    private val context = getApplication<Application>().applicationContext

    //Todo: check that newList is always initialized before usage
    private var newList = mutableListOf<PlantPhoto>()

    private val _newListLiveData = MutableLiveData<MutableList<PlantPhoto>>()
    val newListLiveData: MutableLiveData<MutableList<PlantPhoto>>
        get() = _newListLiveData

    init {
        retrieveFileList()
        Log.i("OnCreate", "mediaList retrieved")
        changeToPlantPhotos(mediaLists)
        Log.i("OnCreate", "function changeToPlantPhoto(mediaLists) executed")
    }

    fun retrieveFileList() {
        viewModelScope.launch {
            try {
                mediaLists = context?.getExternalFilesDir("planio/dataclasses")
                    ?.listFiles()?.toMutableList() ?: mutableListOf()
            }catch (e: Exception) {
                //todo: create a "Directory not found" message in the UI to notify user
                Log.i("OnCreate", "planio/dataclasses directory not found.")
                return@launch
            }
        }

        Log.i("OnCreate", "mediaList created")
        Log.i("OnCreate", "File path: " + context?.getExternalFilesDir("planio/dataclasses").toString())
        Log.i("OnCreate", "mediaLists size: " + mediaLists.size.toString())

        for (item in mediaLists) {
            Log.i("OnCreate", "mediaList.absolutePath: " + item.absolutePath)
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

    fun changeToPlantPhotos(plantList: MutableList<File>) {
        viewModelScope.launch {
            plantList.map {
                val file = FileInputStream(it)
                val inStream = ObjectInputStream(file)
                val item = inStream.readObject() as PlantPhoto
                newList.add(item)
            }

            for (item in newList) {
                Log.i("OnCreate", "PlantFilePath: " + item.plantFilePath.toString())
            }

            newListLiveData.value = newList
            Log.i("OnCreate", "newListLiveData has value.")
        }
    }
}