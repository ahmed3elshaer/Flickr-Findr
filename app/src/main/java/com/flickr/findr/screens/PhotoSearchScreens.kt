package com.flickr.findr.screens

sealed class PhotoSearchScreens(open val route: String) {
    object SearchResult : PhotoSearchScreens(route = "SearchResult")

    class PhotoDetails(
        val title: String,
        val url: String
    ) : PhotoSearchScreens(route = "PhotoDetails?$titleArg={$titleArg}&$urlArg={$urlArg}") {
        override val route = super.route

        companion object {
            const val titleArg = "title"
            const val urlArg = "url"
            const val route = "PhotoDetails?$titleArg={$titleArg}&$urlArg={$urlArg}"
        }
    }
}
