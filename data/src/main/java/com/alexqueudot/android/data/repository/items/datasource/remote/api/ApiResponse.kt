package com.alexqueudot.android.data.repository.items.datasource.remote.api

/**
 * Created by alex on 2019-07-25.
 */

data class ApiResponse<T>(
    val info: ApiInfo?,
    val results: List<T>?
)

data class ApiInfo(
    val count: Int?,
    val pages: Int?,
    val next: String?,
    val prev: String?
)