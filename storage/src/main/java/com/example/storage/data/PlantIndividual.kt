package com.example.storage.data

import java.io.File

data class PlantIndividual (

    val plantId: Int,

    val plantName: String = "",

    val plantFilePath: File,

    val plantType: String
)