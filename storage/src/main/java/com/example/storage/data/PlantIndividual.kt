package com.example.storage.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.File
import java.io.Serializable
@Parcelize
data class PlantIndividual (

    val plantId: Int,

    val plantName: String = "",

    val plantFilePath: File,

    val plantType: String,
) : Serializable, Parcelable