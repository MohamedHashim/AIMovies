package com.example.aimovies.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aimovies.presentation.home.HomeScreen
import com.example.aimovies.presentation.overveiw.OverviewScreen
import com.example.aimovies.presentation.ui.theme.AIMoviesTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val overViewArguments = listOf(
            navArgument("id") {
                type = NavType.LongType
            },
            navArgument("title") {
                type = NavType.StringType
            },
            navArgument("overview") {
                type = NavType.StringType
            },
            navArgument("release_date") {
                type = NavType.StringType
            },
            navArgument("poster_path") {
                type = NavType.StringType
            },
            navArgument("vote_average") {
                type = NavType.StringType
            },
        )

        setContent {
            AIMoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                    ) {
                        composable("home") {
                            HomeScreen { id, title, overview, releaseDate, posterPath, voteAverage ->
                                navController.navigate(
                                    "overview/$id/$title/$overview/$releaseDate/$posterPath/$voteAverage",
                                )
                            }
                        }
                        composable(
                            route = "overview/{id}/{title}/{overview}/{release_date}/{poster_path}/{vote_average}",
                            arguments = overViewArguments,
                        ) { backStackEntry ->
                            val id = backStackEntry.arguments?.getLong("id") ?: 0L
                            val title = backStackEntry.arguments?.getString("title") ?: ""
                            val overview = backStackEntry.arguments?.getString("overview") ?: ""
                            val releaseDate =
                                backStackEntry.arguments?.getString("release_date") ?: ""
                            val posterPath =
                                backStackEntry.arguments?.getString("poster_path") ?: ""
                            val voteAverage =
                                backStackEntry.arguments?.getString("vote_average") ?: ""

                            OverviewScreen(
                                id,
                                title,
                                overview,
                                releaseDate,
                                posterPath,
                                voteAverage,
                                navController,
                            )
                        }
                    }
                }
            }
        }
    }
}
