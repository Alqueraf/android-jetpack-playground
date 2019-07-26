package com.alexqueudot.android.data.repository.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.result.Result
import com.alexqueudot.android.data.repository.items.datasource.memory.MemoryDataSource
import com.alexqueudot.android.data.repository.items.datasource.remote.api.ApiItemsDataSource
import com.alexqueudot.android.data.repository.items.datasource.remote.api.paging.ApiPagingItemsDataSource
import com.alexqueudot.android.data.repository.items.datasource.remote.api.paging.ApiPagingItemsDataSourceFactory
import com.alexqueudot.android.data.repository.items.datasource.remote.api.paging.PagedOutput
import com.alexqueudot.android.data.result.Success
import com.alexqueudot.android.data.result.onSuccess

/**
 * Created by alex on 2019-05-20.
 */

class DataItemsRepository(
    private val remoteDataSource: ApiItemsDataSource,
    private val localDataSource: MemoryDataSource,
    private val pagingDataSourceFactory: ApiPagingItemsDataSourceFactory
) : ItemsRepository {

    override fun getItemsPaginated(pageSize: Int): PagedOutput<Item> {
        val livePagedList = pagingDataSourceFactory.toLiveData(pageSize)
        return PagedOutput(
            pagedList = livePagedList,
            refreshing = switchMap(pagingDataSourceFactory.sourceLiveData) { it.initialLoad },
            dataError = switchMap(pagingDataSourceFactory.sourceLiveData) { it.errors },
            refresh = { pagingDataSourceFactory.sourceLiveData.value?.invalidate() }
        )
    }

    override suspend fun getItems(page: Int?): Result<List<Item>> {
        return localDataSource.getItems().takeIf { it.isNotEmpty() && page == null }?.let {
            Success(data = it)
        } ?: run {
            remoteDataSource.getItems(page).also {
                it.onSuccess { it.takeIf { page == null }?.also { localDataSource.saveItems(it) } }
            }
        }
    }

    override suspend fun getItem(itemId: Int): Result<Item> {
        return localDataSource.getItem(itemId)?.let {
            Success(data = it)
        } ?: remoteDataSource.getItem(itemId)
    }
}