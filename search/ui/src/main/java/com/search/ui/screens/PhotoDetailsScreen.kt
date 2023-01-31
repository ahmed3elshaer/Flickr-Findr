package com.search.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.components.ui.designsystem.LargePhoto
import com.components.ui.designsystem.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailsScreen(
    title: String,
    url: String,
    onBackPressed: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = title,
            onBackPressed = onBackPressed
        )
    }) {
        LargePhoto(
            modifier = Modifier.padding(it),
            url = url
        )
    }
}
