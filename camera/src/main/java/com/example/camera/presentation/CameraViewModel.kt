package com.example.camera.presentation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Environment
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.storage.data.PlantIndividual
import com.example.storage.data.PlantPhoto
import kotlinx.coroutines.launch
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
        Log.i("OnCreate", "IndividualFile path: " + context?.getExternalFilesDir("planio/plants").toString())
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

    //Todo: change this to plant specific SPIdNumber
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

    //Todo: change this to plant specific SPIdNumber
    fun getNewSpIdNumber() : String? {
        val sharedPreferences : SharedPreferences = context.getSharedPreferences(
            "spIdNumber",
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString("spIdNumber", "0")
    }

    //Todo: save image to chosen plant in recycler view
    fun saveImage(IdNumber: Int, photoFile: File, photoId: Int) {

        val newImage = PlantPhoto(IdNumber,  photoFile, photoId)

        val dir = File(
            context.getExternalFilesDir(null), "planio/dataclasses"
        )
        if (!dir.exists()) {
            dir.mkdirs()
            Log.i(SaveTag, dir.absolutePath)
        }
        Log.i(SaveTag, dir.absolutePath)
        Log.i(SaveTag, "${dir.exists()}")

        val dirOne = File(dir, "$photoId")

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
