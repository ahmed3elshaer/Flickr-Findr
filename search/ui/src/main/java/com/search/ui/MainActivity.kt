package com.search.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.components.ui.theme.AppTheme
import com.search.presentation.SearchPhotosViewModel
import com.search.ui.screens.PhotoDetailsScreen
import com.search.ui.screens.PhotoSearchResultsScreen
import com.search.ui.screens.PhotoSearchScreens
import com.search.ui.screens.decode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val viewModel = hiltViewModel<SearchPhotosViewModel>()
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {

                }) { paddingValues ->
                    NavHost(
                        navController,
                        startDestination = PhotoSearchScreens.SearchResult.route,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable(PhotoSearchScreens.SearchResult.route) {
                            PhotoSearchResultsScreen(
                                viewModel = viewModel,
                                onDestination = { destination: PhotoSearchScreens ->
                                    navController.navigate(destination.route)
                                })
                        }
                        composable(
                            route = PhotoSearchScreens.PhotoDetails.route,
                            arguments = listOf(
                                navArgument(PhotoSearchScreens.PhotoDetails.titleArg) {
                                    type = NavType.StringType
                                },
                                navArgument(PhotoSearchScreens.PhotoDetails.urlArg) {
                                    type = NavType.StringType
                                })
                        ) { backStackEntry ->
                            val title =
                                backStackEntry
                                    .arguments
                                    ?.getString(PhotoSearchScreens.PhotoDetails.titleArg)
                                    ?.decode()
                                    ?: throw IllegalArgumentException("Title is required")

                            val url =
                                backStackEntry
                                    .arguments
                                    ?.getString(PhotoSearchScreens.PhotoDetails.urlArg)
                                    ?: throw IllegalArgumentException("Url is required")
                            PhotoDetailsScreen(
                                title, url, onBackPressed = navController::popBackStack
                            )
                        }
                    }
                }
            }
        }
    }
}
