package com.alexqueudot.android.data.repository.source.remote.api

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.data.repository.source.RemoteDataSource
import io.reactivex.Single


/**
 * Created by alex on 2019-05-20.
 */

class ApiDataSource(private val itemsApi: ItemsApi): RemoteDataSource {

    override fun getItems(): Single<List<Item>> {
        return itemsApi.getQuestions(site = "stackoverflow").map { it.items }.map { it.map { apiItem -> apiItem.toItem() } }
    }
}