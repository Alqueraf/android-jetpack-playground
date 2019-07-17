package com.alexqueudot.android.jetpackplayground.items

import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.repository.items.error.ItemsError

/**
 * Created by alex on 2019-07-09.
 */

sealed class ItemListState

object Loading : ItemListState()
data class Available(val items: List<Item>) : ItemListState()
data class Unavailable(val reason: ItemsError? = null) : ItemListState()