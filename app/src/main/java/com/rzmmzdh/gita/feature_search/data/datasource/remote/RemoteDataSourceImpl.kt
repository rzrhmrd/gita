package com.rzmmzdh.gita.feature_search.data.datasource.remote

import com.rzmmzdh.gita.feature_search.data.datasource.remote.dto.RepositorySearchResultDto
import com.rzmmzdh.gita.feature_search.domain.datasource.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val githubService: GitHubRemoteService) :
    RemoteDataSource {
    override suspend fun search(query: String): Response<RepositorySearchResultDto> {
        return githubService.searchRepositories(query)
    }
}