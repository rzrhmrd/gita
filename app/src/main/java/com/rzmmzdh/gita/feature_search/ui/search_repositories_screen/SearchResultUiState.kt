package com.rzmmzdh.gita.feature_search.ui.search_repositories_screen

import com.rzmmzdh.gita.feature_search.domain.model.RepositorySearchResult

data class SearchResultUiState(
    var isLoading: Boolean = false,
    val data: RepositorySearchResult? = null,
    val error: Throwable? = null
)
