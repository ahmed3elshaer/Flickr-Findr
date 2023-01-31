package com.components.ui.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.search.domain.model.SearchTerm

@Composable
fun SearchTerm(
    searchTerm: SearchTerm,
    onClick: (SearchTerm) -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick(searchTerm) }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = searchTerm.text,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface
        )
    }
}