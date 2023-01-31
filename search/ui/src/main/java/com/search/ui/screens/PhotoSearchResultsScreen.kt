package com.search.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.components.ui.designsystem.Error
import com.components.ui.designsystem.Loading
import com.components.ui.designsystem.PhotosSearchField
import com.components.ui.designsystem.SearchTerm
import com.components.ui.designsystem.ThumbnailPhoto
import com.search.domain.model.Photo
import com.search.domain.model.largeUrl
import com.search.domain.model.mediumUrl
import com.search.presentation.SearchAction
import com.search.presentation.SearchPhotosViewModel
import com.search.presentation.SearchState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.search.domain.model.SearchTerm as SearchTermModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PhotoSearchResultsScreen(
    onDestination: (PhotoSearchScreens) -> Unit, viewModel: SearchPhotosViewModel
) {
    val query = remember { mutableStateOf(TextFieldValue()) }
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchState by viewModel.observeState().collectAsState()

    Scaffold(topBar = {
        PhotosSearchField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            query = query.value,
            onTexChange = {
                if (query.value.text != it.text) {
                    query.value = it
                    viewModel.dispatch(
                        SearchAction.GetSearchTerms
                    )
                }
            },
            onClear = { query.value = TextFieldValue() },
            onFocusChange = { focusState ->
                if (focusState.isFocused) {
                    viewModel.dispatch(
                        SearchAction.GetSearchTerms
                    )
                }
            },
            focusRequester = focusRequester
        )
    }) {
        Crossfade(searchState, modifier = Modifier.padding(it)) {
            when (val state = it) {
                is SearchState.Error -> Error(error = state.message)
                SearchState.Loading -> Loading()
                is SearchState.Photos -> PhotoResultsBody(state.photos, onPhotoClicked = { photo ->
                    onDestination(
                        PhotoSearchScreens.PhotoDetails(
                            title = photo.title, url = photo.largeUrl().encode()
                        )
                    )
                })

                is SearchState.RecentSearchTerms -> SearchTermsBody(
                    query.value.text, state.searchTerms
                ) {
                    query.value = TextFieldValue(text = it)
                    viewModel.dispatch(
                        SearchAction.SearchPhotos(
                            it
                        )
                    )
                    coroutineScope.launch {
                        delay(200)
                        focusRequester.freeFocus()
                        keyboardController?.hide()
                    }
                }

                SearchState.Idle -> {}
            }
        }
    }
}

@Composable

internal fun SearchTermsBody(
    query: String, searchTerms: List<SearchTermModel>, onSearchTermSelected: (String) -> Unit
) {
    LazyColumn(horizontalAlignment = Alignment.Start) {
        if (query.isNotBlank()) {
            item {
                SearchTerm(searchTerm = SearchTermModel(text = query, id = "0"),
                    onClick = { onSearchTermSelected(it.text) })
            }
        }
        items(searchTerms) { item: SearchTermModel ->
            SearchTerm(searchTerm = item, onClick = { onSearchTermSelected(it.text) })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PhotoResultsBody(
    photos: Flow<PagingData<Photo>>,
    onPhotoClicked: (Photo) -> Unit
) {
    val pagedPhotos = photos.collectAsLazyPagingItems()
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(lazyPagingItems = pagedPhotos) { value ->
            value?.let {
                ThumbnailPhoto(title = value.title,
                    url = value.mediumUrl(),
                    onClick = { onPhotoClicked(value) })
            }
        }
        when (val state = pagedPhotos.loadState.refresh) { // FIRST LOAD
            is LoadState.Error -> {
                item {
                    Error(state.error.message)
                }
            }

            is LoadState.Loading -> { // Loading UI
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp), text = "Loading"
                        )

                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            else -> {}
        }

        when (val state = pagedPhotos.loadState.append) { // Pagination
            is LoadState.Error -> {
                item {
                    Error(state.error.message)
                }
            }

            is LoadState.Loading -> { // Pagination Loading UI
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Loading")

                        CircularProgressIndicator(color = Color.Black)
                    }
                }
            }

            else -> {}
        }
    }
}

@ExperimentalFoundationApi
internal fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}