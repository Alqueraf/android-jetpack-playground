package com.alexqueudot.android.data.repository.datasource.memory

import com.alexqueudot.android.core.entity.Item
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by alex on 2019-05-20.
 */

class MemoryDataSource {
    private var items: List<Item>? = null

    fun getItems(): Single<List<Item>> {
        return Single.just(items ?: ArrayList())
    }

    fun saveItems(items: List<Item>): Single<Boolean> {
        this.items = items
        return Single.just(true)
    }

    fun getItem(itemId: Int): Maybe<Item> {
        return items?.firstOrNull { it.id == itemId }?.let {
            Maybe.just(it)
        } ?: Maybe.empty()
    }

}