package com.example.camera.presentation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

        val newImage = PlantPhoto(IdNumber, photoFile, photoId)

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

    fun onSelectForPreview(plantIndividual: PlantIndividual){

        _selectForPreview.value = plantIndividual
    }

    fun selectForPreviewComplete() {
        _selectForPreview.value = null
    }

    fun detectEdges(bitmap: Bitmap) {
        val rgba = Mat()
        Utils.bitmapToMat(bitmap, rgba)
        val edges = Mat(rgba.size(), CvType.CV_8UC1)
        Imgproc.cvtColor(rgba, edges, Imgproc.COLOR_RGB2GRAY, 4)
        Imgproc.Canny(edges, edges, 80.0, 100.0)

        // Don't do that at home or work it's for visualization purpose.
//        BitmapHelper.showBitmap(this, bitmap, imageView)
//        val resultBitmap = Bitmap.createBitmap(edges.cols(), edges.rows(), Bitmap.Config.ARGB_8888)
//        Utils.matToBitmap(edges, resultBitmap)
//        BitmapHelper.showBitmap(this, resultBitmap, detectEdgesImageView)
    }

//    fun mImageToImageWithEdge(int: Int) {
//
//    }
}
