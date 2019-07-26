package com.alexqueudot.android.data.repository.items.datasource.remote.api.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.repository.items.datasource.remote.api.ApiEndpoints

/**
 * Created by alex on 2019-07-25.
 */

class ApiPagingItemsDataSourceFactory(
    private val apiEndpoints: ApiEndpoints
): DataSource.Factory<Int, Item>() {

    val sourceLiveData = MutableLiveData<ApiPagingItemsDataSource>()
    override fun create(): DataSource<Int, Item> {
        val source = ApiPagingItemsDataSource(apiEndpoints = apiEndpoints)
        println("Got Source $source")
        sourceLiveData.postValue(source)
        return source
    }
}