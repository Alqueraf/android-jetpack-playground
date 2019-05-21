package com.alexqueudot.android.data.repository

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.data.di.data
import io.reactivex.Single

/**
 * Created by alex on 2019-05-20.
 */

class DataItemsRepository() : ItemsRepository {

    private val remoteDataSource = data().remoteDataSource
    private val localDataSource = data().localDataSource

    override fun getItems(refresh: Boolean): Single<List<Item>> {
        // TODO: Handle API Error
        return if (refresh) {
            remoteDataSource.getItems().doOnSuccess { localDataSource.saveItems(it) }
        } else {
            Single.concat(localDataSource.getItems(), remoteDataSource.getItems())
                .filter { it.isNotEmpty() }
                .firstOrError()
                .doOnSuccess { localDataSource.saveItems(it) }
        }
    }

//    companion object {
//        @Volatile
//        private var _instance: DataItemsRepository? = null
//        val instance: DataItemsRepository
//            get() {
//                return _instance ?: synchronized(this) {
//                    DataItemsRepository().also {
//                        _instance = it
//                    }
//                }
//            }
//    }
}