package com.rzmmzdh.gita.feature_search.domain.repository

import com.rzmmzdh.gita.feature_search.domain.model.Item
import com.rzmmzdh.gita.feature_search.domain.model.RepositorySearchResult
import com.rzmmzdh.gita.feature_search.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {
    suspend fun search(query: String): Flow<Result<RepositorySearchResult>>
    suspend fun getRepo(repo: String): Flow<Result<Item?>>
}