package com.example.aimovies.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.aimovies.presentation.home.HomeScreen
import com.example.aimovies.presentation.overveiw.OverviewScreen
import com.example.aimovies.presentation.ui.theme.AIMoviesTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AIMoviesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                    ) {
                        navigation(
                            startDestination = "home",
                            route = "main"
                        ) {
                            composable("home") { entry ->
                                val viewModel = entry.sharedViewModel<HomeSharedViewModel>(navController)
                                HomeScreen{
                                    viewModel.updateState(it)
                                    navController.navigate("overview")
                                }
                            }
                            composable(
                                route = "overview"
                            ) { entry ->
                                val viewModel = entry.sharedViewModel<HomeSharedViewModel>(navController)
                                val state by viewModel.sharedState.collectAsStateWithLifecycle()
                                OverviewScreen(state){
                                    navController.popBackStack()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
