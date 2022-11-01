package com.rzmmzdh.gita.feature_search.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName
import com.rzmmzdh.gita.feature_search.domain.model.Item
import com.rzmmzdh.gita.feature_search.domain.model.License
import com.rzmmzdh.gita.feature_search.domain.model.Owner

data class ItemDto(
    val id: Int,
    val owner: Owner,
    @SerializedName("full_name")
    val fullName: String,
    val description: String?,
    @SerializedName("forks_count")
    val forksCount: Int,
    val url: String,
    @SerializedName("clone_url")
    val cloneUrl: String,
    val language: String?,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    val license: License?
)

fun ItemDto.asItem() = Item(
    id,
    owner,
    fullName,
    description,
    forksCount,
    url,
    cloneUrl,
    language,
    stargazersCount,
    updatedAt,
    license
)