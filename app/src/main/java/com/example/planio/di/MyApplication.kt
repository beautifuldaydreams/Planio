package com.example.planio.di

import android.app.Application
import com.example.camera.di.CameraComponent
import com.example.camera.di.CameraComponentProvider

class MyApplication: Application(), CameraComponentProvider {

    // Instance of the AppComponent that will be used by all the Activities in the project
    //TODO: create an instance of appComponent
    val appComponent = DaggerAppComponent.factory()

    override fun provideCameraComponent(): CameraComponent {
        return appComponent.cameraComponent().create()
    }
}