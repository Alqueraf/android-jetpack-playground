package com.alexqueudot.android.data.repository.datasource.memory

import com.alexqueudot.android.core.entity.Item

/**
 * Created by alex on 2019-05-20.
 */

class MemoryDataSource {
    private var items: List<Item>? = null

    suspend fun getItems(): List<Item> {
        return items ?: emptyList()
    }

    suspend fun saveItems(items: List<Item>): Boolean {
        this.items = items
        return true
    }

    suspend fun getItem(itemId: Int): Item? {
        return items?.firstOrNull { it.id == itemId }
    }

}