package com.alexqueudot.android.data.repository.source

import com.alexqueudot.android.core.entity.Item
import io.reactivex.Single

/**
 * Created by alex on 2019-05-21.
 */
interface LocalDataSource {
    fun getItems(): Single<List<Item>>
    fun saveItems(items: List<Item>): Single<Boolean>
}