package com.rzmmzdh.gita.feature_search.data.datasource.remote.dto

import com.rzmmzdh.gita.feature_search.domain.model.RepositorySearchResult

data class RepositorySearchResultDto(
    val incomplete_results: Boolean,
    val items: List<ItemDto>,
    val total_count: Int
)


fun RepositorySearchResultDto.asSearchResult() =
    RepositorySearchResult(
        incomplete_results = incomplete_results,
        items = items.map { it.asItem() }, total_count = total_count
    )
