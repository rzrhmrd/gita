package com.rzmmzdh.gita.feature_search.ui.search_repositories

sealed class SearchRepositoriesEvent {
    data class OnSearchQueryChange(val value: String) : SearchRepositoriesEvent()
}
