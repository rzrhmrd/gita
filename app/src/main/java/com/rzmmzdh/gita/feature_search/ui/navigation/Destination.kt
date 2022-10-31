package com.rzmmzdh.gita.feature_search.ui.navigation

sealed class Destination(val route: String) {
    object RepositoryDetail : Destination("repository_detail_screen")
    object SearchRepositories : Destination("repository_search_screen")
}
