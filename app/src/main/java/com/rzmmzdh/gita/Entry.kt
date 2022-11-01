package com.rzmmzdh.gita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.rzmmzdh.gita.common.theme.GitaTheme
import com.rzmmzdh.gita.feature_search.ui.navigation.Destination
import com.rzmmzdh.gita.feature_search.ui.repository_detail.RepositoryDetailScreen
import com.rzmmzdh.gita.feature_search.ui.search_repositories.SearchRepositoriesScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Entry : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberAnimatedNavController()
            GitaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = Destination.SearchRepositories.route
                    ) {
                        composable(
                            Destination.SearchRepositories.route,
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentScope.SlideDirection.Down,
                                    animationSpec = tween(500)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(500)
                                )
                            }) {
                            SearchRepositoriesScreen(navController)
                        }
                        composable(
                            route = Destination.RepositoryDetail.route + "?repo={repo}",
                            arguments = listOf(navArgument("repo") {
                                type = NavType.StringType
                                defaultValue = "repo"
                            }
                            ),
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentScope.SlideDirection.Up,
                                    animationSpec = tween(500)
                                )
                            },
                            exitTransition = {
                                slideOutOfContainer(
                                    AnimatedContentScope.SlideDirection.Down,
                                    animationSpec = tween(500)
                                )
                            }) {
                            RepositoryDetailScreen(navController)
                        }
                    }
                    HideSystemUi()
                }
            }
        }
    }

    @Composable
    private fun HideSystemUi() {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()

        DisposableEffect(systemUiController, useDarkIcons) {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
            onDispose {}
        }
    }
}