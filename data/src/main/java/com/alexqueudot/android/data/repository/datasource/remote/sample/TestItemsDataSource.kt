package com.alexqueudot.android.data.repository.datasource.remote.sample

import TestQuestionsHolder
import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.data.repository.datasource.remote.ApiItemsResponse
import com.google.gson.Gson
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by alex on 2019-05-21.
 */
class TestItemsDataSource {

    fun getItems(): Single<List<Item>> {
        return Single.just(
            Gson().fromJson(TestQuestionsHolder.questions, ApiItemsResponse::class.java).items?.map { it.toItem() }
        )
    }
    fun getItem(itemId: Int): Maybe<Item> {
       return getItems().flatMapMaybe { it.firstOrNull { it.id == itemId }?.let { Maybe.just(it) } ?: Maybe.empty() }
    }

    fun saveItems(items: List<Item>): Single<Boolean> {
        throw NotImplementedError("Can't save items here")
    }
}