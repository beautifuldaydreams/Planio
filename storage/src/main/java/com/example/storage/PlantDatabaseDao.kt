package com.example.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.storage.data.PlantIndividual
import java.io.File

@Dao
interface PlantDatabaseDao {

    @Insert
    fun insert(plant: PlantIndividual)

    @Update
    fun update(plant: PlantIndividual)

    //TODO: Determine whether the app should query plant data through it's id or plantName or plantFilePath
    @Query("SELECT * from PlantIndividual WHERE plantFilePath = :file")
    fun get(file: File): PlantIndividual?

    @Query("DELETE FROM PlantIndividual")
    fun clear()

    @Query("SELECT * FROM PlantIndividual ORDER BY plantId DESC ")
    fun getAllPlants() : LiveData<List<PlantIndividual>>
}