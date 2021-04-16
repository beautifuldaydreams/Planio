package com.lisaschubeka.storage.data

import java.io.File
import java.io.Serializable

data class PlantPhoto(

    val plantId: Int,

    var plantFilePath: File?,

    var photoId: Int,
) : Serializable

