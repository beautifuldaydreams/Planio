package com.example.collection.presentation.individual

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storage.data.PlantIndividual

class CollectionIndividualViewModelFactory(private val plantIndividual: PlantIndividual,
                                            private val application: Application
) : ViewModelProvider.Factory  {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionIndividualViewModel::class.java)) {
            return CollectionIndividualViewModel(plantIndividual, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}