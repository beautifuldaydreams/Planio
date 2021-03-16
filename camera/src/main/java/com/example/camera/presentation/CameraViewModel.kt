package com.example.camera.presentation

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.storage.SharedPreferences.Companion.editSpIdNumber
import com.example.storage.SharedPreferences.Companion.getNewSpIdNumber
import com.example.storage.data.PlantIndividual
import com.example.storage.data.PlantPhoto
import kotlinx.coroutines.launch
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc
import java.io.*


class CameraViewModel(application: Application)  : AndroidViewModel(application) {

    private val SaveTag = "SAVEDIMAGE"
    private val context = getApplication<Application>().applicationContext

    lateinit var spIdNumber : String

    private var plantIndividualList = mutableListOf<PlantIndividual>()
    private var individualFileList = mutableListOf<File>()

    private val _newListIndividualLiveData = MutableLiveData<MutableList<PlantIndividual>>()
    val newListIndividualLiveData: MutableLiveData<MutableList<PlantIndividual>>
        get() = _newListIndividualLiveData

    private val _selectForPreview = MutableLiveData<PlantIndividual>()
    val selectForPreview: LiveData<PlantIndividual>
        get() = _selectForPreview

    init {
        //Todo: change this to plant specific SPIdNumber
        if (!this::spIdNumber.isInitialized) {
            spIdNumber = "0"
        }
        retrievePlantIndividualFileList()
        Log.i("OnCreate", "PlantIndividualFileList retrieved")
        changeToPlantIndividuals(individualFileList)
        Log.i("OnCreate", "function changeToPlantIndividual(individualFileLists) executed")
    }

    fun retrievePlantIndividualFileList() {
        viewModelScope.launch {
            try {
                individualFileList = context?.getExternalFilesDir("planio/plants")
                    ?.listFiles()?.toMutableList() ?: mutableListOf()
            }catch (e: Exception) {
                //todo: create a "Directory not found" message in the UI to notify user
                Log.i("OnCreate", "planio/dataclasses/0 directory not found.")
                return@launch
            }
        }
        Log.i("OnCreate", "individualFileList created")
        Log.i(
            "OnCreate",
            "IndividualFile path: " + context?.getExternalFilesDir("planio/plants").toString()
        )
        Log.i("OnCreate", "individualFileList size: " + individualFileList.size.toString())
        for (item in individualFileList) {
            Log.i("OnCreate", "individualFileList.absolutePath: " + item.absolutePath)
        }
    }

    fun changeToPlantIndividuals(plantList: MutableList<File>) {
        viewModelScope.launch {
            plantList.map {
                val file = FileInputStream(it)
                val inStream = ObjectInputStream(file)
                val item = inStream.readObject() as PlantIndividual
                plantIndividualList.add(item)
            }
            for (item in plantIndividualList) {
                Log.i("OnCreate", "PlantIndividualListPath: " + item.plantFilePath.toString())
            }
            newListIndividualLiveData.value = plantIndividualList
            Log.i("OnCreate", "newListIndividualLiveData has value.")
        }
    }

    //Todo: save image to chosen plant in recycler view
    fun saveImage(photoFile: File) {

        val plantIndi = selectForPreview.value
        val SPNum = getNewSpIdNumber(
            plantIndi?.plantId.toString(), context)?.toInt()
        Log.i(SaveTag, "SPNum key: ${plantIndi?.plantId} SPNum value: $SPNum")
        Log.i(SaveTag, "SPNum key: ${plantIndi?.plantId} SPNum value: $SPNum")

        val newImage = SPNum?.let {
            plantIndi?.plantId?.let { it1 -> PlantPhoto(it1, photoFile, it) }
        }
        Log.i(SaveTag, "newImage.plantId: ${plantIndi?.plantId} newImage.photoId: ${SPNum}")

        val dir = File(
            context.getExternalFilesDir(null), "planio/dataclasses"
        )
        if (!dir.exists()) {
            dir.mkdirs()
            Log.i(SaveTag, dir.absolutePath)
        }
        Log.i(SaveTag, dir.absolutePath)

        val dirOne = File(dir, "${plantIndi?.plantId}")

        if(!dirOne.exists()){
            dir.mkdirs()
            Log.i(SaveTag, "After dirOne made: $dirOne")
        }

        Log.i(SaveTag, dirOne.absolutePath)
        Log.i(SaveTag, dirOne.isFile.toString())
        Log.i(SaveTag, dirOne.isDirectory.toString())

        val dataClassLocation = File(dirOne, "$SPNum")
        Log.i(SaveTag, "After dirOne made: $dataClassLocation")

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

        editSpIdNumber(SPNum.toString(), context)
        Log.i(SaveTag, "AFTER SAVING - SPNum key: ${plantIndi?.plantId} SPNum value: $SPNum")
        Log.i(SaveTag, "Image saved successfully")
    }

    fun onSelectForPreview(plantIndividual: PlantIndividual){

        _selectForPreview.value = plantIndividual
    }

    fun selectForPreviewComplete() {
        _selectForPreview.value = null
    }
}

