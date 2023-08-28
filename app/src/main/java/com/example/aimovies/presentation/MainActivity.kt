package com.example.aimovies.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.aimovies.R
import com.example.aimovies.data.Queries.RECOMMENDATION_QUERY
import com.example.aimovies.presentation.home.HomeScreen
import com.example.aimovies.presentation.overveiw.OverviewScreen
import com.example.aimovies.presentation.ui.theme.AIMoviesTheme
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.bigquery.BigQueryOptions
import com.google.cloud.bigquery.QueryJobConfiguration
import com.google.cloud.bigquery.TableResult
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            val credentials =
                GoogleCredentials.fromStream(resources.openRawResource(R.raw.ai_movies))
            val bigquery = BigQueryOptions.newBuilder().setCredentials(credentials).build().service

            val queryConfig = QueryJobConfiguration.newBuilder(RECOMMENDATION_QUERY)
                .build()

            val result = bigquery.query(queryConfig)
            val jsonString = convertTableResultToJson(result)

            Log.d("result big query", jsonString)
        }
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

    fun convertTableResultToJson(tableResult: TableResult): String {
        val gson = Gson()
        val jsonArray = JsonArray()

        for (row in tableResult.iterateAll()) {
            val jsonObject = JsonObject()
            jsonObject.addProperty("user_id", row["user_id"].longValue)

            val recommendationsArray = JsonArray()
            for (recommendation in row["top_recommendations"].recordValue) {
                val recommendationObject = JsonObject()
                recommendationObject.addProperty(
                    "movie_title",
                    recommendation.recordValue[0].stringValue,
                )
                recommendationObject.addProperty("genre", recommendation.recordValue[1].stringValue)
                recommendationObject.addProperty(
                    "predicted_rating",
                    recommendation.recordValue[2].doubleValue,
                )
                recommendationsArray.add(recommendationObject)
            }
            jsonObject.add("top_recommendations", recommendationsArray)

            jsonArray.add(jsonObject)
        }

        return gson.toJson(jsonArray)
    }
}
