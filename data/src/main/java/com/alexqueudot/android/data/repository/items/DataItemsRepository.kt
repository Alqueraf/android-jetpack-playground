package com.alexqueudot.android.data.repository.items

import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.result.Result
import com.alexqueudot.android.data.repository.items.datasource.memory.MemoryDataSource
import com.alexqueudot.android.data.repository.items.datasource.remote.api.ApiItemsDataSource
import com.alexqueudot.android.data.result.Success
import com.alexqueudot.android.data.result.onSuccess

/**
 * Created by alex on 2019-05-20.
 */

class DataItemsRepository(
    private val remoteDataSource: ApiItemsDataSource,
    private val localDataSource: MemoryDataSource
) : ItemsRepository {

    override suspend fun getItems(refresh: Boolean): Result<List<Item>> {
        return if (refresh) {
            remoteDataSource.getItems().also {
                it.onSuccess { localDataSource.saveItems(it) }
            }
        } else {
            localDataSource.getItems().takeIf { it.isNotEmpty() }?.let {
                Success(data = it)
            } ?: run {
                remoteDataSource.getItems().also {
                    it.onSuccess { localDataSource.saveItems(it) }
                }
            }
        }
    }

    override suspend fun getItem(itemId: Int): Result<Item> {
        return localDataSource.getItem(itemId)?.let {
            Success(data = it)
        } ?: remoteDataSource.getItem(itemId)
    }
}