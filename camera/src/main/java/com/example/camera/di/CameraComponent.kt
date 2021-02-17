package com.example.camera.di

import com.example.camera.presentation.CameraFragment
import dagger.Subcomponent

@Subcomponent
interface CameraComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): CameraComponent
    }

    fun inject(fragment: CameraFragment)
}