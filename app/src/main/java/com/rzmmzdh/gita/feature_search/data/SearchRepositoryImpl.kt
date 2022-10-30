package com.rzmmzdh.gita.feature_search.data

import com.rzmmzdh.gita.feature_search.data.datasource.remote.dto.asSearchResult
import com.rzmmzdh.gita.feature_search.domain.datasource.RemoteDataSource
import com.rzmmzdh.gita.feature_search.domain.model.RepositorySearchResult
import com.rzmmzdh.gita.feature_search.domain.model.Result
import com.rzmmzdh.gita.feature_search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val remote: RemoteDataSource) :
    SearchRepository {
    override suspend fun search(query: String): Flow<Result<RepositorySearchResult>> =
        flow {
            val result = remote.search(query)
            emit(Result.Loading)
            when {
                result.isSuccessful -> {
                    emit(
                        Result.Success(
                            data = remote.search(query).body()?.asSearchResult()
                                ?: RepositorySearchResult()
                        )
                    )
                }
                result.code() in 400..499 -> {
                    emit(
                        Result.Error(
                            Throwable(
                                message =
                                "Network client error"
                            )
                        )
                    )
                }
                result.code() in 500..599 -> {
                    emit(Result.Error(Throwable(message = "Network server error")))
                }
            }
        }
}
