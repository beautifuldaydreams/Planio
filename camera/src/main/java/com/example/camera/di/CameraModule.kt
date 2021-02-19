package com.example.camera.di

import com.example.storage.PlantDatabaseDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class CameraModule {

    @Binds
    abstract fun provideDatabaseDao(plantDatabaseDao: PlantDatabaseDao) :PlantDatabaseDao
}