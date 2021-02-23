package com.example.camera.di

import android.content.Context
import com.example.camera.presentation.CameraFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CameraModule::class])
interface CameraComponent {

    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): CameraComponent
    }

    fun inject(cameraFragment: CameraFragment)
}