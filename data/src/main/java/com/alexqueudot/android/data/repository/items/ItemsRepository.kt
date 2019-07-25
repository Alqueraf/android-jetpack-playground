package com.alexqueudot.android.data.repository.items

import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.result.Result

/**
 * Created by alex on 2019-05-20.
 */
interface ItemsRepository {
    suspend fun getItems(forceRefresh: Boolean = false, page: Int = 0): Result<List<Item>>
    suspend fun getItem(itemId: Int): Result<Item>
}