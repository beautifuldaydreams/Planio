package com.lisaschubeka.camera.presentation

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lisaschubeka.storage.SharedPreferences.Companion.editSpIdNumber
import com.lisaschubeka.storage.SharedPreferences.Companion.getNewSpIdNumber
import com.lisaschubeka.storage.data.PlantIndividual
import com.lisaschubeka.storage.data.PlantPhoto
import kotlinx.coroutines.launch
import java.io.*


class CameraViewModel(application: Application)  : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private lateinit var spIdNumber : String

    private var plantIndividualList = mutableListOf<PlantIndividual>()
    private var individualFileList = mutableListOf<File>()

    private val _newListIndividualLiveData = MutableLiveData<MutableList<PlantIndividual>>()
    val newListIndividualLiveData: MutableLiveData<MutableList<PlantIndividual>>
        get() = _newListIndividualLiveData

    private val _selectForPreview = MutableLiveData<PlantIndividual>()
    val selectForPreview: LiveData<PlantIndividual>
        get() = _selectForPreview

    private val _edgeDetectionImage = MutableLiveData<Bitmap>()
    val edgeDetectionImage: LiveData<Bitmap>
        get() = _edgeDetectionImage

    init {
        if (!this::spIdNumber.isInitialized) {
            spIdNumber = "0"
        }
        selectForPreviewComplete()
        retrievePlantIndividualFileList()
        changeToPlantIndividuals(individualFileList)
    }

    fun onEdgeDetection(bitmap: Bitmap){
        _edgeDetectionImage.value = bitmap
    }

    fun onEdgeDetectionNull(){
        _edgeDetectionImage.value = null
    }

    private fun retrievePlantIndividualFileList() {
        viewModelScope.launch {
            try {
                individualFileList = context?.getExternalFilesDir("planio/plants")
                    ?.listFiles()?.toMutableList() ?: mutableListOf()
            }catch (e: Exception) {
                return@launch
            }
        }
    }

    private fun changeToPlantIndividuals(plantList: MutableList<File>) {
        viewModelScope.launch {
            plantList.map {
                val file = FileInputStream(it)
                val inStream = ObjectInputStream(file)
                val item = inStream.readObject() as PlantIndividual
                plantIndividualList.add(item)
            }
            newListIndividualLiveData.value = plantIndividualList
        }
    }

    fun saveImage(photoFile: File) {

        val plantIndi = selectForPreview.value
        val sPNum = getNewSpIdNumber(
            plantIndi?.plantId.toString(), context, "0")?.toInt()

        val newImage = sPNum?.let {
            plantIndi?.plantId?.let { it1 -> PlantPhoto(it1, photoFile, it) }
        }

        val dir = File(
            context.getExternalFilesDir(null), "planio/dataclasses"
        )
        if (!dir.exists()) { dir.mkdirs() }

        val dirOne = File(dir, "${plantIndi?.plantId}")

        if(!dirOne.exists()){
            dir.mkdirs()
        }
        val dataClassLocation = File(dirOne, "$sPNum")

        editSpIdNumber(plantIndi?.plantId.toString(), context)

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

        editSpIdNumber(sPNum.toString(), context)
    }

    fun onSelectForPreview(plantIndividual: PlantIndividual){
        _selectForPreview.value = plantIndividual
    }

    fun selectForPreviewComplete() {
        _selectForPreview.value = null
    }
}

