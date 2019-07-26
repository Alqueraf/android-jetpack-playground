package com.alexqueudot.android.data.repository.items

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.repository.items.datasource.remote.api.paging.PagedOutput
import com.alexqueudot.android.data.result.Result

/**
 * Created by alex on 2019-05-20.
 */
interface ItemsRepository {
    suspend fun getItems(page: Int? = null): Result<List<Item>>
    suspend fun getItem(itemId: Int): Result<Item>

    fun getItemsPaginated(pageSize: Int): PagedOutput<Item>
}