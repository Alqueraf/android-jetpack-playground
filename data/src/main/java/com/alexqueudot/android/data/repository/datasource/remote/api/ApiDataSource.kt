package com.alexqueudot.android.data.repository.datasource.remote.api

import com.alexqueudot.android.core.entity.Item
import io.reactivex.Maybe
import io.reactivex.Single


/**
 * Created by alex on 2019-05-20.
 */

class ApiDataSource(private val apiEndpoints: ApiEndpoints) {

    fun getItems(): Single<List<Item>> {
        return apiEndpoints.getQuestions(site = "stackoverflow").map { it.items?.map { it.toItem() } ?: ArrayList() }
    }

    fun getItem(itemId: Int): Maybe<Item> {
        return getItems().flatMapMaybe { it.firstOrNull { it.id == itemId }?.let { Maybe.just(it) } ?: Maybe.empty() }
    }
}