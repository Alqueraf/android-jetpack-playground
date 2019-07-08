package com.alexqueudot.android.core.repository

import com.alexqueudot.android.core.entity.Item

/**
 * Created by alex on 2019-05-20.
 */
interface ItemsRepository {

    suspend fun getItems(refresh: Boolean): List<Item>
    suspend fun getItem(itemId: Int): Item?
}