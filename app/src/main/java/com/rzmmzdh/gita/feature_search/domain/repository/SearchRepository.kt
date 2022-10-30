package com.rzmmzdh.gita.feature_search.domain.repository

import com.rzmmzdh.gita.feature_search.domain.model.RepositorySearchResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun search(query: String): Flow<Result<RepositorySearchResult>>
}