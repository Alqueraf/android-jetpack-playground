package com.alexqueudot.android.data.repository.cache

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import io.reactivex.Single

/**
 * Created by alex on 2019-05-20.
 */

class CacheService: ItemsRepository {
    private var items: List<Item>? = null

    override fun getItems(): Single<List<Item>> {
        return Single.just(items ?: ArrayList())
    }

    fun saveItems(items: List<Item>) {
        this.items = items
    }

}