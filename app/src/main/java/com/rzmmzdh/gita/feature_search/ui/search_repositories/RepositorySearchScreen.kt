package com.rzmmzdh.gita.feature_search.ui.search_repositories

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.rzmmzdh.gita.common.theme.jbMono
import com.rzmmzdh.gita.common.util.ConnectionState
import com.rzmmzdh.gita.common.util.connectivityState
import com.rzmmzdh.gita.feature_search.domain.model.Item
import com.rzmmzdh.gita.feature_search.ui.common.colorTransition
import com.rzmmzdh.gita.feature_search.ui.navigation.Destination
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun SearchRepositoriesScreen(
    navController: NavHostController,
    state: RepositorySearchViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()
    val activeConnection by connectivityState()
    val isDeviceConnectedToInternet = activeConnection === ConnectionState.Available
    val focusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            GitaSearchBar(
                modifier = Modifier,
                query = state.searchQuery,
                isLoading = state.searchResult.isLoading,
                onQueryChange = { state.onQueryChange(it) },
                enabled = isDeviceConnectedToInternet,
                onSearch = { focusManager.clearFocus() }
            )
        }
    ) { paddingValues ->
        SearchResult(
            paddingValues = paddingValues,
            result = state.searchResult.data?.items,
            onResultClick = {
                navController.navigate(
                    Destination.RepositoryDetail.route + "?repo=${it?.fullName}"
                )
            }, isDeviceConnected = isDeviceConnectedToInternet
        )
        state.searchResult.error?.let {
            snackbarScope.launch {
                val errorMessage = state.searchResult.error!!.localizedMessage ?: "Unknown error"
                snackbarHostState.showSnackbar(errorMessage)
                state.onErrorShown()
            }
        }

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun GitaSearchBar(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    isLoading: Boolean,
    enabled: Boolean,
    onSearch: () -> Unit
) =
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        title = {
            Column {
                TextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleLarge,
                    placeholder = {
                        Text(
                            text = "Gita",
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    enabled = enabled,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSearch() }),
                )
                if (isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

            }

        })

@Composable
private fun SearchResult(
    paddingValues: PaddingValues,
    result: List<Item>?,
    onResultClick: (Item?) -> Unit,
    isDeviceConnected: Boolean
) {
    if (!isDeviceConnected) {
        OfflinePlaceholder(paddingValues)

    } else if (result.isNullOrEmpty()) {
        EmptyResultPlaceholder(paddingValues)
    } else {
        SearchItem(paddingValues, result, onResultClick)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchItem(
    paddingValues: PaddingValues,
    result: List<Item>?,
    onResultClick: (Item?) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues
                    .calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        result?.let {
            items(result) { item ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(184.dp)
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 4.dp),
                    onClick = { onResultClick(item) },
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Stars(item.stargazersCount)
                            Forks(item.forksCount)
                        }
                        Name(item.fullName)
                        Description(item.description ?: "Description")
                        Language(item.language ?: "Language")

                    }

                }


            }
        }
    }
}

@Composable
private fun Stars(stars: Int) {
    Text(
        text =
        "⭐ $stars", style = MaterialTheme.typography.labelSmall
    )
}

@Composable
private fun Forks(forks: Int) {
    Text(
        text =
        "🧑‍🌾 $forks", style = MaterialTheme.typography.labelSmall
    )
}

@Composable
private fun Name(item: String) {
    Text(
        text = item,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun Description(description: String = "Description") {
    Text(
        text = description,
        modifier = Modifier
            .fillMaxWidth(),
        style = MaterialTheme.typography.titleSmall,
        overflow = TextOverflow.Ellipsis,
        maxLines = 3
    )
}

@Composable
private fun Language(language: String = "Language") {
    Box(
        modifier = Modifier.border(
            width = DividerDefaults.Thickness,
            color = DividerDefaults.color,
            shape = RoundedCornerShape(8.dp)
        )
    ) {
        Text(
            text = language,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(4.dp)
        )

    }
}

@Composable
private fun EmptyResultPlaceholder(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EmptyResultPlaceholder()
    }
}

@Composable
private fun EmptyResultPlaceholder() {
    Text(
        text = "~",
        modifier = Modifier.fillMaxWidth(),
        style = TextStyle(
            fontSize = 248.sp,
            fontFamily = jbMono,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            letterSpacing = 20.sp,
            color = colorTransition(
                initialColor = MaterialTheme.colorScheme.primary,
                targetColor = MaterialTheme.colorScheme.tertiary,
                tweenAnimationDuration = 5000
            )

        )
    )
}

@Composable
private fun OfflinePlaceholder(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val colorTransition = colorTransition(
            initialColor = MaterialTheme.colorScheme.primary,
            targetColor = MaterialTheme.colorScheme.error,
            tweenAnimationDuration = 5000
        )
        Text(
            text = "~",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 248.sp,
                fontFamily = jbMono,
                textAlign = TextAlign.Center,
                lineHeight = 40.sp,
                letterSpacing = 20.sp,
                color = colorTransition
            )
        )
        Text(
            text = "Device is offline. Please check your internet connection.",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleMedium.copy(color = colorTransition)
        )
    }
}