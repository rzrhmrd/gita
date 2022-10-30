package com.rzmmzdh.gita.feature_search.ui.search_repositories_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    var searchPlaceholder = mutableStateOf("search_")
        private set
    var searchResult = mutableStateOf(SearchResultUiState())
        private set

    var searchQuery = mutableStateOf("")
        private set
    var searchJob: Job? = null
        private set
    var errorMessage = MutableStateFlow("")
        private set


    fun onEvent(event: SearchRepositoriesEvent) {
        when (event) {
            is SearchRepositoriesEvent.OnSearchQueryChange -> {
                searchQuery.value = event.value
                if (event.value.isBlank()) {
                    searchResult.value = searchResult.value.copy(data = null)
                }
                search(event.value)
            }
        }
    }

    private fun search(query: String) {
        searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300L)
            searchRepo(query).collectLatest { result ->
                when (result) {
                    is Result.Error -> errorMessage.value =
                        result.exception?.localizedMessage.toString()
                    is Result.Loading -> searchResult.value =
                        searchResult.value.copy(isLoading = true)
                    is Result.Success -> searchResult.value =
                        searchResult.value.copy(data = result.data, isLoading = false)
                }
            }
        }
    }

}