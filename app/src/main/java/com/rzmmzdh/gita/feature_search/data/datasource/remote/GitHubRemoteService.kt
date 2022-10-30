package com.rzmmzdh.gita.feature_search.data.datasource.remote

import com.rzmmzdh.gita.feature_search.data.datasource.remote.dto.RepositorySearchResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubRemoteService {
    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): Response<RepositorySearchResultDto>
}
