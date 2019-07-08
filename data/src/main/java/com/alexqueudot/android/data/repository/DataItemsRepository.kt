package com.alexqueudot.android.data.repository

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.data.repository.datasource.memory.MemoryDataSource
import com.alexqueudot.android.data.repository.datasource.remote.api.ApiDataSource

/**
 * Created by alex on 2019-05-20.
 */

class DataItemsRepository(private val remoteDataSource: ApiDataSource, private val localDataSource: MemoryDataSource) :
    ItemsRepository {

    override suspend fun getItems(refresh: Boolean): List<Item> {
        return if (refresh) {
            remoteDataSource.getItems().also { localDataSource.saveItems(it) }
        } else {
            return localDataSource.getItems().takeIf { it.isNotEmpty() }
                ?: remoteDataSource.getItems().also { localDataSource.saveItems(it) }
        }
    }

    override suspend fun getItem(itemId: Int): Item? {
        return localDataSource.getItem(itemId)
            ?: remoteDataSource.getItem(itemId)
    }
}