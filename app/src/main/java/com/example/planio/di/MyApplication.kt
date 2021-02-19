package com.example.planio.di

//import android.app.Application
//import android.content.Context
//import androidx.core.view.KeyEventDispatcher
//import com.example.camera.di.CameraComponent
//import com.example.camera.di.CameraComponentProvider
//import dagger.BindsInstance
//import dagger.Component
//
//open class MyApplication: Application(), CameraComponentProvider {
//
//    // Instance of the AppComponent that will be used by all the Activities in the project
//    //TODO: create an instance of appComponent
//    val appComponent = DaggerAppComponent.factory().create(applicationContext)
//
//    override fun provideCameraComponent(): CameraComponent {
//        return appComponent.cameraComponent().create()
//    }
//}