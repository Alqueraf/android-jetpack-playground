package com.alexqueudot.android.data.repository.items.datasource.remote.api.paging

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alexqueudot.android.data.result.DataError

/**
 * Created by alex on 2019-07-26.
 */

data class PagedOutput<T>(
    // the LiveData of paged lists for the UI to observe
    val pagedList: LiveData<PagedList<T>>,
    // represents the network request status to show to the user
    val dataError: LiveData<DataError>,
    // represents the refresh status to show to the user. Separate from networkState, this
    // value is importantly only when refresh is requested.
    val refreshing: LiveData<Boolean>,
    // refreshes the whole data and fetches it from scratch.
    val refresh: () -> Unit
)