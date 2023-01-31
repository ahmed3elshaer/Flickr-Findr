package com.search.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.components.ui.designsystem.PhotosSearchField
import com.components.ui.theme.AppTheme
import com.search.presentation.SearchAction
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
                val searchState by viewModel.observeState().collectAsState()
                val query = remember { mutableStateOf(TextFieldValue()) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        PhotosSearchField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            query = query.value,
                            onTexChange = { query.value = it },
                            onClear = { query.value = TextFieldValue() },
                            onFocusChange = { focusState ->
                                if (focusState.isFocused) {
                                    viewModel.dispatch(
                                        SearchAction.GetSearchTerms
                                    )
                                } else {
                                    viewModel.dispatch(
                                        SearchAction.SearchPhotos(query.value.text)
                                    )
                                }
                            },
                            onSearch = {
                                viewModel.dispatch(
                                    SearchAction.SearchPhotos(query.value.text)
                                )
                            }
                        )
                    }
                ) { paddingValues ->
                    NavHost(
                        navController,
                        startDestination = PhotoSearchScreens.SearchResult.route,
                        modifier = Modifier
                            .padding(paddingValues)
                    ) {
                        composable(PhotoSearchScreens.SearchResult.route) {
                            PhotoSearchResultsScreen(
                                query = query.value.text,
                                searchState = searchState,
                                onSearchTermSelected = {
                                    viewModel.dispatch(
                                        SearchAction.SearchPhotos(
                                            it
                                        )
                                    )
                                },
                                onDestination =
                                { destination: PhotoSearchScreens ->
                                    navController.navigate(destination.route)
                                }
                            )
                        }
                        composable(
                            route = PhotoSearchScreens.PhotoDetails.route,
                            arguments = listOf(
                                navArgument(PhotoSearchScreens.PhotoDetails.titleArg) {
                                    type = NavType.StringType
                                },
                                navArgument(PhotoSearchScreens.PhotoDetails.urlArg) {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val title =
                                backStackEntry
                                    .arguments
                                    ?.getString(PhotoSearchScreens.PhotoDetails.titleArg)
                                    ?.decode()
                                    ?: throw IllegalArgumentException("Title is required")

                            val url =
                                backStackEntry.arguments
                                    ?.getString(PhotoSearchScreens.PhotoDetails.urlArg)
                                    ?: throw IllegalArgumentException("Url is required")
                            PhotoDetailsScreen(
                                title,
                                url,
                                onBackPressed = navController::popBackStack
                            )
                        }
                    }
                }
            }
        }
    }
}
