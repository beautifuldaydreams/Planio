package com.example.collection.presentation.overview

import android.app.Application
import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.*
import androidx.navigation.fragment.navArgs
import com.example.navigation.NavigationFlow

class CollectionOverviewViewModel(): ViewModel() {

    private lateinit var mediaList: MutableList<File>

    init {
//        getImagesFromExtStorage()
    }


//    private fun getImagesFromExtStorage() {
//        // Walk through all files in the root directory
//        // We reverse the order of the list to present the last photos first
//        mediaList = context?.getExternalFilesDir("Planio")?.listFiles { file ->
//            EXTENSION_WHITELIST.contains(file.extension.toUpperCase(Locale.ROOT))
//        }?.sortedDescending()?.toMutableList() ?: mutableListOf()
//    }
}