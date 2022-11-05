package com.rzmmzdh.gita.feature_search.data

import com.rzmmzdh.gita.feature_search.data.datasource.remote.dto.asItem
import com.rzmmzdh.gita.feature_search.data.datasource.remote.dto.asSearchResult
import com.rzmmzdh.gita.feature_search.domain.datasource.RemoteDataSource
import com.rzmmzdh.gita.feature_search.domain.model.Item
import com.rzmmzdh.gita.feature_search.domain.model.RepositorySearchResult
import com.rzmmzdh.gita.feature_search.domain.model.Result
import com.rzmmzdh.gita.feature_search.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(private val remote: RemoteDataSource) :
    GitHubRepository {
    override suspend fun search(query: String): Flow<Result<RepositorySearchResult>> =
        flow {
            val result = remote.searchRepo(query)
            emit(Result.Loading)
            when {
                result.isSuccessful -> {
                    emit(
                        Result.Success(
                            data = remote.searchRepo(query).body()?.asSearchResult()
                                ?: RepositorySearchResult()
                        )
                    )
                }
                result.code() in 400..499 -> {
                    emit(
                        Result.Error(
                            Throwable(
                                message =
                                "Network logger: Code(${result.code()}) | API rate limit remaining(${
                                    result.headers()["x-ratelimit-remaining"]
                                })"
                            )
                        )
                    )
                }
                result.code() in 500..599 -> {
                    emit(
                        Result.Error(
                            Throwable(
                                message = "Network logger: Code(${result.code()}) | API rate limit remaining(${
                                    result.headers()["x-ratelimit-remaining"]
                                })"
                            )
                        )
                    )
                }
                else -> {
                    emit(
                        Result.Error(
                            Throwable(
                                message = "Network logger: Code(${result.code()}) | API rate limit remaining(${
                                    result.headers()["x-ratelimit-remaining"]
                                })"

                            )
                        )
                    )
                }
            }
        }

    override suspend fun getRepo(repo: String): Flow<Result<Item?>> =
        flow {
            emit(Result.Loading)
            try {
                emit(Result.Success(data = remote.getRepo(repo).body()?.asItem()))
            } catch (e: Exception) {
                emit(Result.Error(exception = e))
            }
        }
}

