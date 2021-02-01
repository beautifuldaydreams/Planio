package com.example.navigation

sealed class NavigationFlow {
    object CameraFlow : NavigationFlow()
    object CollectionFlow : NavigationFlow()
    object HomeFlow : NavigationFlow()
}