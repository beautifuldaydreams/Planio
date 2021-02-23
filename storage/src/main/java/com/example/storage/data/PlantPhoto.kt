package com.example.storage.data

import java.io.File
import java.io.Serializable

data class PlantPhoto (

    val plantId: Int,

    var plantName: String = "",

    var plantFilePath: File,

    var plantType: String,
) : Serializable

