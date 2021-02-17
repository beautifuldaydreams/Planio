package com.example.collection.presentation.overview

import androidx.lifecycle.ViewModel
import java.io.File

class CollectionOverviewViewModel(rootDirectory: String) : ViewModel() {

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