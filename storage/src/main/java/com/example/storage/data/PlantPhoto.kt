package com.example.storage.data

import android.graphics.drawable.Drawable
import java.io.File
import java.io.Serializable

data class PlantPhoto(

    val plantId: Int,

    var plantFilePath: File?,

    var photoId: Int,
) : Serializable

