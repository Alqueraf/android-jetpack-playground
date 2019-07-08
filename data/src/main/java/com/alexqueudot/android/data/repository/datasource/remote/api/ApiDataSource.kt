package com.alexqueudot.android.data.repository.datasource.remote.api

import com.alexqueudot.android.core.entity.Item


/**
 * Created by alex on 2019-05-20.
 */

class ApiDataSource(private val apiEndpoints: ApiEndpoints) {

    suspend fun getItems(): List<Item> {
        return apiEndpoints.getQuestions(site = "stackoverflow").items?.map { it.toItem() } ?: emptyList()
    }

    suspend fun getItem(itemId: Int): Item? {
        return getItems().firstOrNull { it.id == itemId }
    }
}