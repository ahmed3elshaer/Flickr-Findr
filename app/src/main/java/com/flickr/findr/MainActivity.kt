package com.flickr.findr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.flickr.findr.screens.PhotoSearchScreens
import com.flickr.findr.theme.AppTheme
import com.search.presentation.SearchPhotosViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val viewModel = hiltViewModel<SearchPhotosViewModel>()
                Scaffold(
                    bottomBar = {}
                ) { paddingValues ->
                    NavHost(
                        navController,
                        startDestination = PhotoSearchScreens.SearchResult.route,
                        modifier = Modifier
                            .padding(paddingValues)
                    ) {

                    }
                }
            }
        }
    }
}
