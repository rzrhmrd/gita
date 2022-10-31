package com.rzmmzdh.gita.feature_search.domain.usecase

import com.rzmmzdh.gita.feature_search.domain.model.Item
import com.rzmmzdh.gita.feature_search.domain.model.Result
import com.rzmmzdh.gita.feature_search.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRepo @Inject constructor(private val repository: GitHubRepository) {
    suspend operator fun invoke(repo: String): Flow<Result<Item?>> {
        return repository.getRepo(repo)
    }
}