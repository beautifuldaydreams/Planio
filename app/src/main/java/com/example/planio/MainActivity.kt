package com.example.planio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.core.R
import com.example.navigation.NavigationFlow
import com.example.navigation.Navigator
import com.example.navigation.ToFlowNavigatable
//import com.example.planio.di.DaggerAppComponent

class MainActivity : AppCompatActivity(), ToFlowNavigatable {
    private val navigator: Navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {

//        (applicationContext as MyApplication)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)

        navigator.navController = navController
    }

    override fun navigateToFlow(flow: NavigationFlow) {
        navigator.navigateToFlow(flow)
    }
}