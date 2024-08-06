package com.example.spacenewsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spacenewsapp.ui.Screen
import com.example.spacenewsapp.ui.detail.DetailScreen
import com.example.spacenewsapp.ui.detail.DetailViewModel
import com.example.spacenewsapp.ui.home.HomeScreen
import com.example.spacenewsapp.ui.home.HomeViewModel
import com.example.spacenewsapp.ui.theme.SpaceNewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpaceNewsAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.HomeScreen.route,
                ) {
                    composable(
                        route = Screen.HomeScreen.route
                    ) {
                        HomeScreen(
                            navController = navController
                        )
                    }
                    composable(
                        route = Screen.DetailScreen.route + "/{id}"
                    ) {
                        val viewModel: DetailViewModel = hiltViewModel()
                        DetailScreen(viewModel)
                    }
                }
            }
        }
    }
}