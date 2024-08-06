package com.example.spacenewsapp.ui

sealed class Screen (
    val route: String
) {
    object HomeScreen: Screen("home_screen")
    object DetailScreen: Screen("detail_screen")
}