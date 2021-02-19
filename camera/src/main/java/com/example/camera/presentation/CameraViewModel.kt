package com.example.camera.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.storage.PlantDatabase
import com.example.storage.PlantDatabaseDao
import javax.inject.Inject

class CameraViewModel @Inject constructor(
    val plantDatabaseDao: PlantDatabaseDao,
    application: Application) : AndroidViewModel(application) {

}