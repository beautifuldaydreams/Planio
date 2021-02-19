package com.example.storage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.storage.data.PlantIndividual

@Dao
interface PlantDatabaseDao {

    @Insert
    fun insert(plant: PlantIndividual)

    @Update
    fun update(plant: PlantIndividual)

    @Query("SELECT * from Plant_Files WHERE plantFilePath = :file")
    fun get(file: String): PlantIndividual?

    @Query("DELETE FROM Plant_Files")
    fun clear()

    @Query("SELECT * FROM Plant_Files ORDER BY plantId DESC ")
    fun getAllPlants() : LiveData<List<PlantIndividual>>
}