package com.rzmmzdh.gita.feature_search.domain.model

data class Item(
    val id: Int,
    val owner: Owner,
    val full_name: String,
    val description: String,
    val forks_count: Int,
    val url: String,
    val git_url: String,
    val language: String,
    val stargazers_count: Int,
    val updated_at: String,
    val license: License
)
