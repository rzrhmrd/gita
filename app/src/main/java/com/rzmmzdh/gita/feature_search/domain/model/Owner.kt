package com.rzmmzdh.gita.feature_search.domain.model

data class Owner(
    val id: Int,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    val gravatar_id: String,
    val html_url: String,
    val repos_url: String,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
)
