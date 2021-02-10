package com.example.collection.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CollectionOverviewViewModelFactory(private val rootDirectory: String): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionOverviewViewModel::class.java)) {
            return CollectionOverviewViewModel(rootDirectory) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
