package com.example.camera.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.storage.PlantDatabase
import com.example.storage.PlantDatabaseDao

class CameraViewModel(
    val database: PlantDatabaseDao,
    application: Application) : AndroidViewModel(application) {

}