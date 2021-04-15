package com.lisasb.navigation

import androidx.navigation.NavController


class Navigator {
    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) = when (navigationFlow) {
        NavigationFlow.CameraFlow -> navController.navigate(MainNavGraphDirections.actionGlobalCameraFlow())
        NavigationFlow.CollectionFlow -> navController.navigate(MainNavGraphDirections.actionGlobalCollectionFlow())
        NavigationFlow.HomeFlow -> navController.navigate(MainNavGraphDirections.actionGlobalHomeFlow())
    }
}