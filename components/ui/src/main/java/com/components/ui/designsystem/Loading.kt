package com.components.ui.designsystem

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Loading() {
    CircularProgressIndicator()
}

@Preview("Loading")
@Composable
fun LoadingPreview() {
    Loading()
}
