package com.rzmmzdh.gita.feature_search.data.datasource.remote.dto

import com.rzmmzdh.gita.feature_search.domain.model.Item
import com.rzmmzdh.gita.feature_search.domain.model.License
import com.rzmmzdh.gita.feature_search.domain.model.Owner

data class ItemDto(
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

fun ItemDto.asItem() = Item(
    id,
    owner,
    full_name,
    description,
    forks_count,
    url,
    git_url,
    language,
    stargazers_count,
    updated_at,
    license
)