package com.alexqueudot.android.data.repository

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.data.repository.cache.CacheService
import com.alexqueudot.android.data.repository.remote.ApiService
import io.reactivex.Single

/**
 * Created by alex on 2019-05-20.
 */

class DataItemsRepository private constructor() : ItemsRepository {

    private val apiService = ApiService()
    private val cacheService = CacheService()

    override fun getItems(): Single<List<Item>> {
        // TODO: Return an `Either`
        // TODO: Save to cache only after querying from RemoteService
        return Single.concat(cacheService.getItems(), apiService.getItems())
            .filter { it.isNotEmpty() }
            .firstOrError()
            .doOnSuccess(cacheService::saveItems)
    }

    companion object {
        @Volatile
        private var _instance: DataItemsRepository? = null
        val instance: DataItemsRepository
            get() {
                return _instance ?: synchronized(this) {
                    DataItemsRepository().also {
                        _instance = it
                    }
                }
            }
    }
}