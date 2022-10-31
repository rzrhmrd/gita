package com.rzmmzdh.gita.feature_search.ui.search_repositories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzmmzdh.gita.feature_search.domain.model.RepositorySearchResult
import com.rzmmzdh.gita.feature_search.domain.model.Result
import com.rzmmzdh.gita.feature_search.domain.usecase.SearchRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositorySearchViewModel @Inject constructor(private val searchRepo: SearchRepo) :
    ViewModel() {
    var searchResult by mutableStateOf(SearchResultUiState())
        private set

    var searchQuery by mutableStateOf("")
        private set
    var searchJob: Job? = null
        private set

    private fun search(query: String) {
        searchQuery = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)
            searchRepo(query).collectLatest { result ->
                searchResult = when (result) {
                    is Result.Error -> {
                        searchResult.copy(error = result.exception, isLoading = false)
                    }

                    is Result.Loading -> searchResult.copy(isLoading = true)
                    is Result.Success -> searchResult.copy(
                        data = result.data,
                        isLoading = false,
                        error = null
                    )
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
