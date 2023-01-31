package com.components.ui.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String = "",
    onBackPressed: () -> Unit
) {
    MediumTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable { onBackPressed() },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Arrow"
            )
        }
    )
}