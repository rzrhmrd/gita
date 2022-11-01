package com.rzmmzdh.gita.feature_search.ui.repository_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzmmzdh.gita.feature_search.domain.model.Item
import com.rzmmzdh.gita.feature_search.domain.model.Result
import com.rzmmzdh.gita.feature_search.domain.usecase.GetRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryDetailViewModel @Inject constructor(
    private val getRepo: GetRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val repo: String = checkNotNull(savedStateHandle["repo"])

    var repoDetailState by mutableStateOf(RepoDetailState())
        private set

    init {
        viewModelScope.launch {
            getRepo(repo).collectLatest { result ->
                repoDetailState = when (result) {
                    is Result.Error -> repoDetailState.copy(
                        isLoading = false,
                        detail = null,
                        error = result.exception
                    )
                    is Result.Loading -> repoDetailState.copy(
                        isLoading = true,
                        detail = null,
                        error = null
                    )
                    is Result.Success -> repoDetailState.copy(
                        isLoading = false,
                        detail = result.data,
                        error = null
                    )
                }
            }
        }
    }
}

data class RepoDetailState(
    val isLoading: Boolean = false,
    val detail: Item? = null,
    val error: Throwable? = null
)
