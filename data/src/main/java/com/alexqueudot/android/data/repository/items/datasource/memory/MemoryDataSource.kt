package com.alexqueudot.android.data.repository.items.datasource.memory

import com.alexqueudot.android.data.model.Item


/**
 * Created by alex on 2019-05-20.
 */

class MemoryDataSource {
    private var items = ArrayList<Item>()

    suspend fun getItems(): List<Item> {
        return items
    }

    suspend fun saveItems(items: List<Item>): Boolean {
        if (items.containsAll(this.items)) {
            this.items = ArrayList(items)
        } else {
            this.items.addAll(items)
        }
        return true
    }

    suspend fun getItem(itemId: Int): Item? {
        return items.firstOrNull { it.id == itemId }
    }

}