package com.rzmmzdh.gita.feature_search.data.datasource.remote.dto

import com.rzmmzdh.gita.feature_search.domain.model.Owner

data class OwnerDto(
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

fun OwnerDto.asOwner() = Owner(
    id,
    avatar_url,
    followers_url,
    following_url,
    gravatar_id,
    html_url,
    repos_url,
    starred_url,
    subscriptions_url,
    type,
    url
)