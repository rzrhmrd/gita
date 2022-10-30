package com.rzmmzdh.gita.feature_search.domain.usecase

import com.rzmmzdh.gita.feature_search.domain.model.RepositorySearchResult
import com.rzmmzdh.gita.feature_search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositories @Inject constructor(private val repository: SearchRepository) {
    suspend operator fun invoke(query: String): Flow<Result<RepositorySearchResult>> {
        return repository.search(query)
    }
}