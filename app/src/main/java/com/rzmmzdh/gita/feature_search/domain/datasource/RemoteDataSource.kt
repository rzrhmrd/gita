package com.rzmmzdh.gita.feature_search.domain.datasource

import com.rzmmzdh.gita.feature_search.data.datasource.remote.dto.RepositorySearchResultDto
import com.rzmmzdh.gita.feature_search.domain.model.RepositorySearchResult
import retrofit2.Response

interface RemoteDataSource {
    suspend fun search(query: String): Response<RepositorySearchResultDto>
}
