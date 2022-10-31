package com.rzmmzdh.gita.feature_search.data.datasource.remote

import com.rzmmzdh.gita.feature_search.data.datasource.remote.dto.ItemDto
import com.rzmmzdh.gita.feature_search.data.datasource.remote.dto.RepositorySearchResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubRemoteService {
    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): Response<RepositorySearchResultDto>

    @GET("repos/{repo}")
    suspend fun getRepo(
        @Path("repo", encoded = true) repo: String
    ): Response<ItemDto>
}
