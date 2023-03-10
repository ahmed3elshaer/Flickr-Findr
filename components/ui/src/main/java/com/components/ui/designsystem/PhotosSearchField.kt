package com.components.ui.designsystem

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.components.ui.R

@Composable
fun PhotosSearchField(
    modifier: Modifier = Modifier,
    query: TextFieldValue,
    onTexChange: (TextFieldValue) -> Unit,
    onClear: () -> Unit,
    onFocusChange: (FocusState) -> Unit,
    focusRequester: FocusRequester
) {
    OutlinedTextField(
        modifier = modifier
            .onFocusChanged(onFocusChange)
            .focusRequester(focusRequester),
        value = query,
        onValueChange = onTexChange,
        label = { Text(text = stringResource(id = R.string.search_photos)) },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.ImageSearch,
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.None),
        trailingIcon = {
            IconButton(onClick = onClear) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                )
            }
        }
    )
}

@Preview
@Composable
fun PhotoSearchFieldPreview() {
    var query by remember { mutableStateOf(TextFieldValue()) }
    PhotosSearchField(
        modifier = Modifier.padding(16.dp),
        query = query,
        onTexChange = { query = it },
        onClear = { query = TextFieldValue() },
        onFocusChange = {},
        focusRequester = FocusRequester()
    )
}
