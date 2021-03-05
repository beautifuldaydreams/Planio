package com.example.collection.presentation.overview

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.collection.R
import com.example.storage.data.PlantIndividual
import com.example.storage.data.PlantPhoto
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import java.io.*

class CollectionOverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "CollectionOverviewViewModel"

    lateinit var mediaLists: MutableList<File>
    private val context = getApplication<Application>().applicationContext
//    var dialog : Dialog

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

//        dialog = Dialog(application)
    }

    //Todo: change to new filing system
    fun retrieveFileList() {
        viewModelScope.launch {
            try {
                mediaLists = context?.getExternalFilesDir("planio/dataclasses/1")
                    ?.listFiles()?.toMutableList() ?: mutableListOf()
            }catch (e: Exception) {
                //todo: create a "Directory not found" message in the UI to notify user
                Log.i("OnCreate", "planio/dataclasses/0 directory not found.")
                return@launch
            }
        }

        Log.i("OnCreate", "mediaList created")
        Log.i("OnCreate", "File path: " + context?.getExternalFilesDir("planio/dataclasses/1").toString())
        Log.i("OnCreate", "mediaLists size: " + mediaLists.size.toString())

        for (item in mediaLists) {
            Log.i("OnCreate", "mediaList.absolutePath: " + item.absolutePath)
        }
    }

    //Todo: change for new filing system
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

    fun makeNewPlant(name: String) {

        //Todo: make plantID SharedPreference
        //Todo: get plantFilePath to dataclass PlantPhoto based on plantId

        val plntIndiSPNum = getNewSpIdNumber()?.toInt()

        val plantPhotoFilePath = File(context.getExternalFilesDir(null), "planio/dataclasses")
        if (!plantPhotoFilePath.exists()) {
            plantPhotoFilePath.mkdirs()
        }
        val specificPlantPath = File(plantPhotoFilePath, "$plntIndiSPNum")
        if(!specificPlantPath.exists()){
            specificPlantPath.mkdirs()
        }

        val newPlantIndividual = plntIndiSPNum?.let { PlantIndividual(it, name, specificPlantPath, "") }

        val dir = File(
            context.getExternalFilesDir(null), "planio/plants"
        )
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val dirOne = File(dir, "$plntIndiSPNum")

        if (dirOne.exists()) {
            dirOne.delete()
            dirOne.createNewFile()
        } else{
            dirOne.createNewFile()
        }

        val plantFile = FileOutputStream(dirOne, true)
        val outStream = ObjectOutputStream(plantFile)

        // Method for serialization of object
        outStream.writeObject(newPlantIndividual)
        outStream.close()
        plantFile.close()
        Log.i(TAG, "Image saved successfully ${dirOne.absolutePath}")

        editSpIdNumber()
    }

    private fun editSpIdNumber() {
        val sharedPreferences : SharedPreferences = context.getSharedPreferences(
            "plntIndiSPNum",
            Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        var num = sharedPreferences.getString("plntIndiSPNum", "0")?.toInt()
        if (num != null) {
            num++
        }
        editor.putString("plntIndiSPNum", num.toString())
        editor.apply()
    }

    private fun getNewSpIdNumber() : String? {
        val sharedPreferences : SharedPreferences = context.getSharedPreferences(
            "plntIndiSPNum",
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString("plntIndiSPNum", "0")
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
}