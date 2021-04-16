package com.lisaschubeka.storage.data

import java.io.File
import java.io.Serializable

data class PlantIndividual (
    val plantId: Int,

    val plantName: String = "",

    val plantFilePath: File,

    val plantType: String
) : Serializable