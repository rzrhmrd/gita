package com.rzmmzdh.gita

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rzmmzdh.gita.feature_search.ui.Destination
import com.rzmmzdh.gita.feature_search.ui.search_repositories_screen.SearchRepositoriesScreen
import com.rzmmzdh.gita.theme.GitaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            GitaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Destination.SearchRepositories.route
                    ) {
                        composable(Destination.SearchRepositories.route) {
                            SearchRepositoriesScreen(navController)
                        }
                    }
                }
            }
        }
    }
}