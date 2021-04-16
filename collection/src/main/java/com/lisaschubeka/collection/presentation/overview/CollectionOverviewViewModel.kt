package com.lisaschubeka.collection.presentation.overview

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lisaschubeka.collection.R
import com.lisaschubeka.storage.SharedPreferences.Companion.editSpIdNumber
import com.lisaschubeka.storage.SharedPreferences.Companion.getNewSpIdNumber
import com.lisaschubeka.storage.data.PlantIndividual
import com.lisaschubeka.storage.data.PlantPhoto
import kotlinx.coroutines.launch
import java.io.*


class CollectionOverviewViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var mediaLists: MutableList<File>
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

    private var placeHolderPlantPhoto: PlantPhoto = PlantPhoto(0, null, 0)

    init {
        retrieveFileList()
        changeToPlantIndividuals(mediaLists)
    }

    fun deleteSelectedPlantIndividual(plantIndividual: PlantIndividual) {

        val dir = plantIndividual.plantId
        val plantList = context?.getExternalFilesDir("planio/dataclasses/$dir")
            ?.listFiles()?.toMutableList() ?: mutableListOf()

        for (item in plantList) {
            val file = FileInputStream(item)
            val inStream = ObjectInputStream(file)
            val item2 = inStream.readObject() as PlantPhoto
            item2.plantFilePath?.delete()
            item.delete()
        }

        plantIndividual.plantFilePath.delete()
        val plantIndiDel = context?.getExternalFilesDir("planio/plants/$dir")
        plantIndiDel?.delete()

        retrieveFileList()
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
                return@launch
            }
        }
    }

    fun retrievePlantList(plantIndividual: PlantIndividual) {
        val dataClassNum = plantIndividual.plantId
        viewModelScope.launch {
            mediaPlantList = context?.getExternalFilesDir("planio/dataclasses/$dataClassNum")
                ?.listFiles()?.sortedDescending()?.toMutableList() ?: mutableListOf()
        }
    }

    fun changeToPlantPhotos(plantList: MutableList<File>) {
        newPhotoList.clear()
        for (item in plantList) {
            val file = FileInputStream(item)
            val inStream = ObjectInputStream(file)
            val item2 = inStream.readObject() as PlantPhoto
            newPhotoList.add(item2)
        }
            if (newPhotoList.isNotEmpty()) {
                newPhotoList.last()
            } else {
                newPhotoList.add(placeHolderPlantPhoto)
            }
            _listPlantPhoto.value = newPhotoList
    }

    private fun changeToPlantIndividuals(plantList: MutableList<File>) {
        newList.clear()
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

        val plantPhotoFilePath = File(context.getExternalFilesDir(null), "planio/dataclasses")
        if (!plantPhotoFilePath.exists()) {
            plantPhotoFilePath.mkdirs()
        }
        val specificPlantPath = File(plantPhotoFilePath, "$plntIndiSPNum")
        if(!specificPlantPath.exists()){
            specificPlantPath.mkdirs()
        }

        val newPlantIndividual = plntIndiSPNum?.let { PlantIndividual(
            it,
            name,
            specificPlantPath,
            ""
        ) }

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

        var isStorageExist = false
        var isStorageWritable = false
        val state = Environment.getExternalStorageState()

        if (bit == null) {
            return
        }
        val matrix = Matrix()
        matrix.postRotate(90F)
        val bitmap = Bitmap.createBitmap(bit, 0, 0, bit.width, bit.height, matrix, true)
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null

        if (Environment.MEDIA_MOUNTED == state) {
            isStorageWritable = true
            isStorageExist = isStorageWritable
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            isStorageExist = true
            isStorageWritable = false
            Toast.makeText(context, "Storage is read only", Toast.LENGTH_SHORT).show()
        } else {
            isStorageWritable = false
            isStorageExist = isStorageWritable
            Toast.makeText(context, "Storage is not exist", Toast.LENGTH_SHORT).show()
        }

        if (isStorageExist && isStorageWritable){
            try{
                var imageUri2 : Uri? = null
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    context?.contentResolver?.also { resolver ->
                        val contentValues = ContentValues().apply {
                            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                            put(
                                MediaStore.MediaColumns.RELATIVE_PATH,
                                Environment.DIRECTORY_PICTURES
                            )
                        }
                        val imageUri: Uri? =
                            resolver.insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                contentValues
                            )
                        fos = imageUri?.let { resolver.openOutputStream(it) }
                        imageUri2= imageUri
                    }

                    MediaScannerConnection.scanFile(context, arrayOf(imageUri2.toString()), null
                    ) { path, uri ->
                        Log.i("ExternalStorage", "Scanned $path:")
                        Log.i("ExternalStorage", "-> uri=$uri")
                    }

                } else {
                    val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "Camera"
                    val file = File(imagesDir)
                    if (!file.exists()) {
                        file.mkdir()
                    }
                    val image = File(imagesDir, filename)
                    fos = FileOutputStream(image)
                    Log.i("SAVEPHOTO", image.absolutePath)
                    MediaScannerConnection.scanFile(context, arrayOf(image.toString()), null
                    ) { path, uri ->
                        Log.i("ExternalStorage", "Scanned $path:")
                        Log.i("ExternalStorage", "-> uri=$uri")
                    }
                }
                fos?.use {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
                Toast.makeText(
                    context,
                    "Image Downloaded",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (exception: Exception){
                Toast.makeText(
                    context,
                    "Image failed to download",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}