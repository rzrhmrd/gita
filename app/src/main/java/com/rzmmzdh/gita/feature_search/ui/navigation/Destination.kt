package com.rzmmzdh.gita.feature_search.ui.navigation

sealed class Destination(val route: String) {
    object RepositoryDetail : Destination("repository_detail_screen") {
        fun withName(name: String): String {
            return "${this.route}?repo=$name"
        }
    }

    object SearchRepositories : Destination("repository_search_screen")
}
