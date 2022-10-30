package com.rzmmzdh.gita.feature_search.ui.navigation

sealed class Destination(val route: String) {
    object SearchRepositories : Destination("search_repositories")
}
