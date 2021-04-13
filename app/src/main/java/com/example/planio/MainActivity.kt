package com.example.planio

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.core.R
import com.example.navigation.NavigationFlow
import com.example.navigation.Navigator
import com.example.navigation.ToFlowNavigatable

class MainActivity : AppCompatActivity(), ToFlowNavigatable {
    private val navigator: Navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY)
        supportActionBar?.hide()
        actionBar?.hide()

        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)

        navigator.navController = navController
    }

    override fun navigateToFlow(flow: NavigationFlow) {
        navigator.navigateToFlow(flow)
    }
}