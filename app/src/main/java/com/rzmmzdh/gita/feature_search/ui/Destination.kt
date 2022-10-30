package com.rzmmzdh.gita.feature_search.ui

sealed class Destination(val route: String) {
    object SearchRepositories : Destination("search_repositories")
}
