package com.alexqueudot.android.data.repository.source.memory

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.data.repository.source.LocalDataSource
import io.reactivex.Single

/**
 * Created by alex on 2019-05-20.
 */

class MemoryDataSource: LocalDataSource {
    private var items: List<Item>? = null

    override fun getItems(): Single<List<Item>> {
        println("Getting items from MemoryDataSource")
        return Single.just(items ?: ArrayList())
    }

    override fun saveItems(items: List<Item>): Single<Boolean> {
        this.items = items
        return Single.just(true)
    }

}