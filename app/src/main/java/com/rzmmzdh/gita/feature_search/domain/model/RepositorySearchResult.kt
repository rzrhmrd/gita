package com.rzmmzdh.gita.feature_search.domain.model

data class RepositorySearchResult(
    val incomplete_results: Boolean = false,
    val items: List<Item> = emptyList(),
    val total_count: Int = 0
)