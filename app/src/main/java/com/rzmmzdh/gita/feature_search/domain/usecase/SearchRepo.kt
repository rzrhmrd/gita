package com.rzmmzdh.gita.feature_search.domain.usecase

import com.rzmmzdh.gita.feature_search.domain.model.RepositorySearchResult
import com.rzmmzdh.gita.feature_search.domain.model.Result
import com.rzmmzdh.gita.feature_search.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepo @Inject constructor(private val repository: GitHubRepository) {
    suspend operator fun invoke(query: String): Flow<Result<RepositorySearchResult>> {
        return repository.search(query)
    }
}