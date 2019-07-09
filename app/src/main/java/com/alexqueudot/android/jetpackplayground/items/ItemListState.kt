package com.alexqueudot.android.jetpackplayground.items

import com.alexqueudot.android.data.model.Item

/**
 * Created by alex on 2019-07-09.
 */

sealed class ItemListState

object Loading : ItemListState()
data class Available(val items: List<Item>) : ItemListState()
object Unavailable : ItemListState()