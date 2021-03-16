package com.example.collection.presentation.individual

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.storage.data.PlantIndividual
import com.example.storage.data.PlantPhoto
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

class CollectionIndividualViewModel(plantIndividual: PlantIndividual,
                                     app: Application) : AndroidViewModel(app) {

    private val debug1 = "DEBUG1"

    private val context = getApplication<Application>().applicationContext
    lateinit var mediaPlantList: MutableList<File>
    private var newPhotoList = mutableListOf<PlantPhoto>()

    private val _listPlantPhoto = MutableLiveData<MutableList<PlantPhoto>>()
    val listPlantPhoto: LiveData<MutableList<PlantPhoto>>
        get() = _listPlantPhoto

    private val _selectForNav = MutableLiveData<PlantIndividual>()
    val selectForNav: LiveData<PlantIndividual>
        get() = _selectForNav

    init {
        retrievePlantList(plantIndividual)
        changeToPlantPhotos(mediaPlantList)
    }


    fun onSelectForNav(plantIndividual: PlantIndividual){
        _selectForNav.value = plantIndividual
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
        viewModelScope.launch {
            plantList.map {
                val file = FileInputStream(it)
                val inStream = ObjectInputStream(file)
                val item = inStream.readObject() as PlantPhoto
                newPhotoList.add(item)
            }

            for (item in newPhotoList) {
                Log.i(debug1, "PlantPhotoListPath: " + item.plantFilePath.toString())
            }

            _listPlantPhoto.value = newPhotoList
            Log.i(debug1, "listPlantPhoto is empty? ${listPlantPhoto.value?.isEmpty()}")
        }
    }

}