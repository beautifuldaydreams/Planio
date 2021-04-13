package com.example.collection.presentation.overview

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.collection.R
import com.example.storage.SharedPreferences.Companion.editSpIdNumber
import com.example.storage.SharedPreferences.Companion.getNewSpIdNumber
import com.example.storage.data.PlantIndividual
import com.example.storage.data.PlantPhoto
import kotlinx.coroutines.launch
import java.io.*

class CollectionOverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val debug3 = "DEBUG3"

    lateinit var mediaLists: MutableList<File>
    lateinit var mediaPlantList: MutableList<File>
    var newPhotoList = mutableListOf<PlantPhoto>()
    private var newList = mutableListOf<PlantIndividual>()

    private val context = getApplication<Application>().applicationContext

    private val _navigateToSelectedPlant = MutableLiveData<PlantIndividual>()
    val navigateToSelectedPlant: LiveData<PlantIndividual>
        get() = _navigateToSelectedPlant

    private val _newListLiveData = MutableLiveData<MutableList<PlantIndividual>>()
    val newListLiveData: LiveData<MutableList<PlantIndividual>>
        get() = _newListLiveData

    private val _listPlantPhoto = MutableLiveData<MutableList<PlantPhoto>>()
    val listPlantPhoto: MutableLiveData<MutableList<PlantPhoto>>
        get() = _listPlantPhoto

    private val _plantPhotoDisplay = MutableLiveData<PlantPhoto>()
    val plantPhotoDisplay: LiveData<PlantPhoto>
        get() = _plantPhotoDisplay

    var placeHolderPlantPhoto: PlantPhoto = PlantPhoto(0, null, 0)

    init {
        retrieveFileList()
        changeToPlantIndividuals(mediaLists)
        Log.i("OnCreate", "mediaList retrieved")
        Log.i("OnCreate", "function changeToPlantPhoto(mediaLists) executed")
    }

    fun deleteSelectedPlantIndividual(plantIndividual: PlantIndividual) {

        val dir = plantIndividual.plantId
        Log.i(debug3, dir.toString())
        val plantList = context?.getExternalFilesDir("planio/dataclasses/$dir")
            ?.listFiles()?.toMutableList() ?: mutableListOf()

        for (item in plantList) {
            Log.i(debug3, "plantList.absolutePath: " + item.absolutePath)
        }

        for (item in plantList) {
            val file = FileInputStream(item)
            val inStream = ObjectInputStream(file)
            val item2 = inStream.readObject() as PlantPhoto
            Log.i(debug3, item2.plantFilePath.toString())
            item2.plantFilePath?.delete()
            item.delete()
        }

        for (item in plantList) {
            Log.i(debug3, "AFTER DEL plantList.absolutePath: " + item.absolutePath)
        }

        plantIndividual.plantFilePath.delete()
        val plantIndiDel = context?.getExternalFilesDir("planio/plants/$dir")
        plantIndiDel?.delete()

        retrieveFileList()
        for (item in mediaLists) {
            Log.i(debug3, "BigMediaList.absolutePath: " + item.absolutePath)
        }
        changeToPlantIndividuals(mediaLists)
    }

    fun deleteSelectedPlantPhoto(imgUrl: PlantPhoto) {
        val photoId = imgUrl.plantId
        val photoId2 = imgUrl.photoId
        val location = context?.getExternalFilesDir("planio/dataclasses/$photoId")
        val slocation = File(location, "$photoId2")
        imgUrl.plantFilePath?.delete()
        slocation.delete()

        val plantList = context?.getExternalFilesDir("planio/dataclasses/$photoId")
            ?.listFiles()?.toMutableList() ?: mutableListOf()
        changeToPlantPhotos(plantList)
    }

    fun displayPlantDetails(plantIndividual: PlantIndividual) {
        _navigateToSelectedPlant.value = plantIndividual
    }

    fun displayPlantDetailsComplete() {
        _navigateToSelectedPlant.value = null
    }

    fun displayPlantPhoto(plantPhoto: PlantPhoto) {
        _plantPhotoDisplay.value = plantPhoto
    }

    private fun retrieveFileList() {
        viewModelScope.launch {
            try {
                mediaLists = context?.getExternalFilesDir("planio/plants")
                    ?.listFiles()?.sortedDescending()?.toMutableList() ?: mutableListOf()
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
            if (newPhotoList.isNotEmpty()) {
                newPhotoList.last()
            } else {
                newPhotoList.add(placeHolderPlantPhoto)
            }
            Log.i(debug1, "PlantPhotoDisplay is empty? ${plantPhotoDisplay.value?.plantFilePath}")
            _listPlantPhoto.value = newPhotoList
            Log.i(debug1, "listPlantPhoto is empty? ${listPlantPhoto.value?.isEmpty()}")
    }

    fun changeToPlantIndividuals(plantList: MutableList<File>) {
        newList.clear()
        for (item in plantList) {
        }

        plantList.map {
            val file = FileInputStream(it)
            val inStream = ObjectInputStream(file)
            val item = inStream.readObject() as PlantIndividual
            newList.add(item)
        }

        _newListLiveData.value = newList
    }

    fun makeNewPlant(name: String) {

        val plntIndiSPNum = getNewSpIdNumber(context.getString(R.string.plntIndiSPNum), context)?.toInt()
        val plantPhotoSPKey = getNewSpIdNumber(context.getString(R.string.plntIndiSPNum), context)?.toInt()
        val plantPhotoSPValue = getNewSpIdNumber(plantPhotoSPKey.toString(), context)?.toInt()

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

        outStream.writeObject(newPlantIndividual)
        outStream.close()
        plantFile.close()

        editSpIdNumber("plntIndiSPNum", context)

        retrieveFileList()
        changeToPlantIndividuals(mediaLists)
    }

    fun saveMediaToStorage(bit: Bitmap?) {

        if (bit == null) {
            return
        }
        val matrix = Matrix()
        matrix.postRotate(90F)
        val bitmap = Bitmap.createBitmap(bit, 0, 0, bit.width, bit.height, matrix, true)
        //Generating a file name
        val filename = "${System.currentTimeMillis()}.jpg"

        //Output stream
        var fos: OutputStream? = null

        //For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //getting the contentResolver
            context?.contentResolver?.also { resolver ->

                //Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    //putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                //Inserting the contentValues to contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                //Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        Toast.makeText(
            context,
            "Image Downloaded",
            Toast.LENGTH_SHORT
        ).show()
    }
}