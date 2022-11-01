package com.rzmmzdh.gita.feature_search.domain.model

data class Item(
    val id: Int,
    val owner: Owner,
    val fullName: String,
    val description: String?,
    val forksCount: Int,
    val url: String,
    val cloneUrl: String,
    val language: String?,
    val stargazersCount: Int,
    val updatedAt: String,
    val license: License?
)
