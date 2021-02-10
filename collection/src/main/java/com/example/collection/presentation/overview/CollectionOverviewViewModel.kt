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

class CollectionOverviewViewModel(rootDirectory: String): ViewModel() {

    private lateinit var mediaList: MutableList<File>
    private val root = rootDirectory

    init {
        getImagesFromExtStorage()
    }

    private fun getImagesFromExtStorage() {
        // Get root directory of media from navigation arguments
        val rootDirect = File(root)

        // Walk through all files in the root directory
        // We reverse the order of the list to present the last photos first
        mediaList = rootDirect.listFiles { file ->
            EXTENSION_WHITELIST.contains(file.extension.toUpperCase(Locale.ROOT))
        }?.sortedDescending()?.toMutableList() ?: mutableListOf()
    }
}