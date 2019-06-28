package com.alexqueudot.android.core

import com.alexqueudot.android.core.entity.Item
import org.junit.Test

import org.junit.Assert.*

/**
 * Created by alex on 2019-06-28.
 */

class ItemUnitTest {

    @Test
    fun createsEmptyItem() {
        val item = Item(123, null, null, null, null)
        assertEquals(item.id,123)
    }
}