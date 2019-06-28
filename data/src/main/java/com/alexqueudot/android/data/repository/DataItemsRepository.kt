package com.alexqueudot.android.data.repository

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.data.di.data
import com.alexqueudot.android.data.repository.datasource.memory.MemoryDataSource
import com.alexqueudot.android.data.repository.datasource.remote.api.ApiDataSource
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by alex on 2019-05-20.
 */

class DataItemsRepository(private val remoteDataSource: ApiDataSource = data().apiDataSource, private val localDataSource: MemoryDataSource = data().memoryDataSource) : ItemsRepository {

    override fun getItems(refresh: Boolean): Single<List<Item>> {
        return if (refresh) {
            remoteDataSource.getItems().doOnSuccess { localDataSource.saveItems(it) }
        } else {
            Single.concat(localDataSource.getItems(), remoteDataSource.getItems())
                .filter { it.isNotEmpty() }
                .firstOrError()
                .doOnSuccess { localDataSource.saveItems(it) }
        }
    }

    override fun getItem(itemId: Int): Single<Item> {
        return Maybe.concat(localDataSource.getItem(itemId), remoteDataSource.getItem(itemId))
            .firstOrError()
    }
}