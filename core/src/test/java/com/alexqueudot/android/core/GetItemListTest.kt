package com.alexqueudot.android.core

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.core.usecase.GetItemListUseCase
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

/**
 * Created by alex on 2019-06-29.
 */

class GetItemListTest {
    @Mock
    private lateinit var mockItemsRepository: ItemsRepository

    @Before
    fun setUp() {
        mockItemsRepository = mock(ItemsRepository::class.java)
    }

    @Test
    fun shouldRetrieveItemList() {
        GetItemListUseCase(mockItemsRepository, true)
        verify(mockItemsRepository).getItems(true)
        verifyNoMoreInteractions(mockItemsRepository)
    }
}