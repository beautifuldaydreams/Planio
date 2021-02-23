package com.example.storage.data

import java.io.File

data class PlantIndividual (

    val plantId: Long = 0L,

    val plantName: String = "",

    val plantFilePath: File,

    val plantType: String
)