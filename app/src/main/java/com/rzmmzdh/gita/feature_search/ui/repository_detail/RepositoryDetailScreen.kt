package com.rzmmzdh.gita.feature_search.ui.repository_detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.rzmmzdh.gita.common.theme.jbMono

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryDetailScreen(
    navController: NavHostController,
    state: RepositoryDetailViewModel = hiltViewModel()
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        RepoDetail(
            paddingValues,
            stars = state.repoDetailState.detail?.stargazers_count ?: 0,
            forks = state.repoDetailState.detail?.forks_count ?: 0,
            avatar = state.repoDetailState.detail?.owner?.avatar_url,
            name = state.repoDetailState.detail?.full_name ?: "name",
            description = state.repoDetailState.detail?.description ?: "description",
            language = state.repoDetailState.detail?.language ?: "language",
            license = state.repoDetailState.detail?.license?.name ?: "license"
        )
    }
}

@Composable
private fun RepoDetail(
    paddingValues: PaddingValues,
    stars: Int = 0,
    forks: Int = 0,
    avatar: String?,
    name: String = "name",
    description: String = "description",
    language: String = "language",
    license: String = "license"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Card(
                modifier = Modifier
                    .size(124.dp)
                    .clip(CircleShape)
                    .border(
                        width = DividerDefaults.Thickness,
                        color = DividerDefaults.color,
                        shape = CircleShape
                    )
            ) {
                AsyncImage(
                    model = avatar,
                    contentDescription = "owner_avatar",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                )
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "⭐ $stars",
                style = TextStyle(
                    fontFamily = jbMono,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            )
            Text(
                text = "🧑‍🌾 $forks",
                style = TextStyle(
                    fontFamily = jbMono,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
        Divider(modifier = Modifier.padding(vertical = 16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = name,
            style = TextStyle(
                fontFamily = jbMono,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            style = TextStyle(
                fontFamily = jbMono,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            ),
            maxLines = 6
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.border(
                width = DividerDefaults.Thickness,
                color = DividerDefaults.color,
                shape = RoundedCornerShape(12.dp)
            )
        ) {
            Text(
                text = language,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = jbMono,
                    fontWeight = FontWeight.Light
                ),
                modifier = Modifier.padding(6.dp)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = license,
            style = TextStyle(
                fontFamily = jbMono,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            ),
        )

    }
}