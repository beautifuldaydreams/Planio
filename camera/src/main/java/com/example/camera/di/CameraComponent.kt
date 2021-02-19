package com.example.camera.di

import com.example.camera.presentation.CameraFragment
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [CameraModule::class])
interface CameraComponent {

    fun inject(cameraFragment: CameraFragment)
}