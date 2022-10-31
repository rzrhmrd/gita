package com.rzmmzdh.gita.feature_search.ui.search_repositories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzmmzdh.gita.feature_search.domain.model.RepositorySearchResult
import com.rzmmzdh.gita.feature_search.domain.model.Result
import com.rzmmzdh.gita.feature_search.domain.usecase.SearchRepositories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchRepositoriesViewModel @Inject constructor(private val searchRepo: SearchRepositories) :
    ViewModel() {
    var searchResult by mutableStateOf(SearchResultUiState())
        private set

    var searchQuery by mutableStateOf("")
        private set
    var searchJob: Job? = null
        private set
    var errorMessage by mutableStateOf("")
        private set

    private fun search(query: String) {
        searchQuery = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)
            searchRepo(query).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        errorMessage = result.exception?.localizedMessage.toString()
                        searchResult = searchResult.copy(isLoading = false)
                    }

                    is Result.Loading -> searchResult =
                        searchResult.copy(isLoading = true)
                    is Result.Success -> searchResult =
                        searchResult.copy(data = result.data, isLoading = false)
                }
            }
        }
    }

    fun onQueryChange(value: String) {
        searchQuery = value
        if (value.isBlank()) {
            searchResult = searchResult.copy(data = null)
        }
        search(value)
    }

    fun onErrorShown() {
        searchResult = searchResult.copy(error = null)
    }
}

data class SearchResultUiState(
    var isLoading: Boolean = false,
    val data: RepositorySearchResult? = null,
    val error: Throwable? = null
)
