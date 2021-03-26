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
import com.example.collection.presentation.individual.CollectionIndividualFragment
import com.example.collection.presentation.individual.CollectionIndividualFragmentArgs
import com.example.storage.SharedPreferences.Companion.editSpIdNumber
import com.example.storage.SharedPreferences.Companion.getNewSpIdNumber
import com.example.storage.data.PlantIndividual
import com.example.storage.data.PlantPhoto
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import java.io.*

class CollectionOverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "CollectionOverviewViewModel"
    private val debug1 = "DEBUG1"

    lateinit var mediaLists: MutableList<File>
    lateinit var mediaPlantList: MutableList<File>
    private var newPhotoList = mutableListOf<PlantPhoto>()
    private var newList = mutableListOf<PlantIndividual>()

    private val context = getApplication<Application>().applicationContext

    private val _navigateToSelectedPlant = MutableLiveData<PlantIndividual>()
    val navigateToSelectedPlant: LiveData<PlantIndividual>
        get() = _navigateToSelectedPlant

    private val _newListLiveData = MutableLiveData<MutableList<PlantIndividual>>()
    val newListLiveData: LiveData<MutableList<PlantIndividual>>
        get() = _newListLiveData

    private val _listPlantPhoto = MutableLiveData<MutableList<PlantPhoto>>()
    val listPlantPhoto: LiveData<MutableList<PlantPhoto>>
        get() = _listPlantPhoto

    private val _plantPhotoDisplay = MutableLiveData<PlantPhoto>()
    val plantPhotoDisplay: LiveData<PlantPhoto>
        get() = _plantPhotoDisplay

    //Todo: all viewModelScopes in here are redundant since they run on the main thread anyways.
    //Todo: analyse if functions can be placed on IO thread to improve performance

    init {
        retrieveFileList()
        changeToPlantIndividuals(mediaLists)
        Log.i("OnCreate", "mediaList retrieved")
        Log.i("OnCreate", "function changeToPlantPhoto(mediaLists) executed")
    }

    fun displayPlantDetails(plantIndividual: PlantIndividual) {
        _navigateToSelectedPlant.value = plantIndividual
    }

    fun displayPlantDetailsComplete() {
        _navigateToSelectedPlant.value = null
    }


    fun retrieveFileList() {
        viewModelScope.launch {
            try {
                mediaLists = context?.getExternalFilesDir("planio/plants")
                    ?.listFiles()?.sortedDescending()?.toMutableList() ?: mutableListOf()

                for (item in mediaLists) {
                    Log.i(TAG, "BigMediaList.absolutePath: " + item.absolutePath)
                }
            }catch (e: Exception) {
                //todo: create a "Directory not found" message in the UI to notify user
                Log.i("OnCreate", "planio/dataclasses/0 directory not found.")
                return@launch
            }
        }

        Log.i("OnCreate", "mediaList created")
        Log.i("OnCreate", "File path: " + context?.getExternalFilesDir("planio/dataclasses/1").toString())
        Log.i("OnCreate", "mediaLists size: " + mediaLists?.size.toString())
    }

    fun retrievePlantList(plantIndividual: PlantIndividual) {
        val dataClassNum = plantIndividual.plantId
        viewModelScope.launch {
            mediaPlantList = context?.getExternalFilesDir("planio/dataclasses/$dataClassNum")
                ?.listFiles()?.sortedDescending()?.toMutableList() ?: mutableListOf()
            for (i in mediaPlantList) {
                Log.i(debug1, "ABSOLUTEPATH: ${i.absolutePath}")
            }
        }
    }

    fun changeToPlantPhotos(plantList: MutableList<File>) {
        newPhotoList.clear()
        for (item in plantList) {
            val file = FileInputStream(item)
            val inStream = ObjectInputStream(file)
            val item = inStream.readObject() as PlantPhoto
            newPhotoList.add(item)
        }

            for (item in newPhotoList) {
                Log.i(debug1, "PlantPhotoListPath: " + item.plantFilePath.toString())
            }
            _plantPhotoDisplay.value = newPhotoList.last()
            Log.i(debug1, "PlantPhotoDisplay is empty? ${plantPhotoDisplay.value?.plantFilePath}")
            _listPlantPhoto.value = newPhotoList
            Log.i(debug1, "listPlantPhoto is empty? ${listPlantPhoto.value?.isEmpty()}")
    }

    fun changeToPlantIndividuals(plantList: MutableList<File>) {

        for (item in plantList) {
            Log.i(TAG, "change to PlantIndividuals.absolutePath: " + item.absolutePath)
        }

        plantList.map {
            val file = FileInputStream(it)
            val inStream = ObjectInputStream(file)
            val item = inStream.readObject() as PlantIndividual
            newList.add(item)
        }

        for (item in newList) {
            Log.i(TAG, "PLANTINDIVIDUAL LOCATION: ${item.plantId}")
            Log.i(TAG, "PlantIndividualListPath: " + item.plantFilePath.toString())
        }

        _newListLiveData.value = newList
        Log.i("OnCreate", "newListIndividualLiveData has value.")
    }

    fun makeNewPlant(name: String) {

        val plntIndiSPNum = getNewSpIdNumber(context.getString(R.string.plntIndiSPNum), context)?.toInt()
        val plantPhotoSPKey = getNewSpIdNumber(context.getString(R.string.plntIndiSPNum), context)?.toInt()
        val plantPhotoSPValue = getNewSpIdNumber(plantPhotoSPKey.toString(), context)?.toInt()

        Log.i(TAG, "plntIndiSPNum: $plntIndiSPNum")
        Log.i(TAG, "plantPhotoSPKey: $plantPhotoSPKey")
        Log.i(TAG, "plantPhotoSPValue: $plantPhotoSPValue")

        val plantPhotoFilePath = File(context.getExternalFilesDir(null), "planio/dataclasses")
        if (!plantPhotoFilePath.exists()) {
            plantPhotoFilePath.mkdirs()
        }
        val specificPlantPath = File(plantPhotoFilePath, "$plntIndiSPNum")
        Log.i(TAG, "PLANTPHOTOSPVALUE: ${plantPhotoSPValue}")
        Log.i(TAG, "PLANTIndiSPNum: ${plntIndiSPNum}")
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

        editSpIdNumber("plntIndiSPNum", context)

        Log.i(TAG, "plntIndiSPNum: $plntIndiSPNum")
        Log.i(TAG, "plantPhotoSPKey: $plantPhotoSPKey")
        Log.i(TAG, "plantPhotoSPValue: $plantPhotoSPValue")
    }
}