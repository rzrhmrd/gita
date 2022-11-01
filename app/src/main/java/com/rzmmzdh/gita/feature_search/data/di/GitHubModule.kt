package com.rzmmzdh.gita.feature_search.data.di

import com.rzmmzdh.gita.BuildConfig
import com.rzmmzdh.gita.feature_search.data.GitHubRepositoryImpl
import com.rzmmzdh.gita.feature_search.data.datasource.remote.GitHubRemoteService
import com.rzmmzdh.gita.feature_search.data.datasource.remote.RemoteDataSourceImpl
import com.rzmmzdh.gita.feature_search.domain.datasource.RemoteDataSource
import com.rzmmzdh.gita.feature_search.domain.repository.GitHubRepository
import com.rzmmzdh.gita.feature_search.domain.usecase.GetRepo
import com.rzmmzdh.gita.feature_search.domain.usecase.GitHubUseCases
import com.rzmmzdh.gita.feature_search.domain.usecase.SearchRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GitHubModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return OkHttpClient.Builder().addInterceptor(logger)
            .addInterceptor(Interceptor { chain ->
                val request = chain.request()
                request.newBuilder()
                    .addHeader(
                        "Authorization", BuildConfig.GITHUB_API_KEY
                    ).build()
                return@Interceptor chain.proceed(request)
            }).build()
    }

    @Provides
    @Singleton
    fun provideRemoteClient(httpClient: OkHttpClient): GitHubRemoteService {
        return Retrofit.Builder().baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
            .create(GitHubRemoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteSearchDataSource(apiService: GitHubRemoteService): RemoteDataSource {
        return RemoteDataSourceImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(dataSource: RemoteDataSource): GitHubRepository {
        return GitHubRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideGitHubUseCases(repository: GitHubRepository): GitHubUseCases {
        return GitHubUseCases(
            searchRepo = SearchRepo(repository),
            getRepo = GetRepo(repository)
        )

    }
}