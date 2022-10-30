package com.rzmmzdh.gita.feature_search.data.datasource.remote.dto

import com.rzmmzdh.gita.feature_search.domain.model.License

data class LicenseDto(
    val key: String,
    val name: String,
    val html_url: String,
    val url: String
)

fun LicenseDto.asLicense() = License(
    key, name, html_url, url
)