package com.search.ui.screens

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.components.ui.designsystem.Empty
import com.components.ui.designsystem.Error
import com.components.ui.designsystem.Loading
import com.components.ui.designsystem.MediumPhoto
import com.components.ui.designsystem.SearchTerm
import com.search.domain.model.Photo
import com.search.domain.model.mediumUrl
import com.search.domain.model.largeUrl
import com.search.presentation.SearchState
import kotlinx.coroutines.flow.Flow
import com.search.domain.model.SearchTerm as SearchTermModel

@Composable
fun PhotoSearchResultsScreen(
    query: String,
    onSearchTermSelected: (String) -> Unit,
    searchState: SearchState,
    onDestination: (PhotoSearchScreens) -> Unit
) {
    Crossfade(searchState) {
        when (val state = it) {
            is SearchState.Error -> Error(error = state.message)
            SearchState.Loading -> Loading()
            is SearchState.Photos -> PhotoResultsBody(state.photos, onPhotoClicked = { photo ->
                onDestination(
                    PhotoSearchScreens.PhotoDetails(
                        title = photo.title,
                        url = photo.largeUrl().encode()
                    )
                )
            })

            is SearchState.RecentSearchTerms -> SearchTermsBody(
                query,
                state.searchTerms,
                onSearchTermSelected
            )

            SearchState.Idle -> {}
        }
    }
}

@Composable

internal fun SearchTermsBody(
    query: String,
    searchTerms: List<SearchTermModel>,
    onSearchTermSelected: (String) -> Unit
) {
    LazyColumn() {
        if (query.isNotBlank()) {
            item {
                SearchTerm(
                    searchTerm = SearchTermModel(text = query, id = "0"),
                    onClick = { onSearchTermSelected(it.text) }
                )
            }
        }
        items(searchTerms) { item: SearchTermModel ->
            SearchTerm(
                searchTerm = item,
                onClick = { onSearchTermSelected(it.text) }
            )
        }
    }
}

@Composable

internal fun PhotoResultsBody(
    photos: Flow<PagingData<Photo>>,
    onPhotoClicked: (Photo) -> Unit
) {
    val scrollState = rememberScrollState()
    val pagedPhotos = photos.collectAsLazyPagingItems()
    LazyColumn {
        if (pagedPhotos.itemCount == 0) {
            item { Empty() }
        }
        itemsIndexed(pagedPhotos) { _: Int, value: Photo? ->
            value?.let {
                MediumPhoto(
                    title = value.title,
                    url = value.mediumUrl(),
                    onClick = { onPhotoClicked(value) }
                )
            }
        }
        when (val state = pagedPhotos.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                item {
                    Error(state.error.message)
                }
            }

            is LoadState.Loading -> { // Loading UI
                item {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refreshing"
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
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
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
