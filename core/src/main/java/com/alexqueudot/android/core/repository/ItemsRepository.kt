package com.alexqueudot.android.core.repository

import com.alexqueudot.android.core.entity.Item
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by alex on 2019-05-20.
 */
interface ItemsRepository {

    fun getItems(refresh: Boolean): Single<List<Item>>
    fun getItem(itemId: Int): Single<Item>
}