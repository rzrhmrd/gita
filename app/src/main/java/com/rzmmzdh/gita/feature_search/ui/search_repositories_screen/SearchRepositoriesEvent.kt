package com.rzmmzdh.gita.feature_search.ui.search_repositories_screen

sealed class SearchRepositoriesEvent {
    data class OnSearchQueryChange(val value: String) : SearchRepositoriesEvent()
}
