package com.alexqueudot.android.data.unit

import com.alexqueudot.android.data.unit.di.testModule
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.repository.items.datasource.memory.MemoryDataSource
import io.reactivex.observers.TestObserver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject


/**
 * Created by alex on 2019-06-28.
 */

@ExperimentalCoroutinesApi
class MemoryDataSourceUnitTest : KoinTest {

    private val memoryDataSource by inject<MemoryDataSource>()
    private val testItems = listOf(
        Item(1, "Item 1", null, 1, true),
        Item(2, "Item 2", null, 1, true)
    )

    @Before
    fun before() {
        startKoin {
            modules(testModule)
        }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun getsItemsFromMemory() = runBlockingTest {
        assert(memoryDataSource.getItems().isEmpty())
    }

    @Test
    fun savesItemsToMemory() = runBlockingTest {
        assert(memoryDataSource.getItems().isEmpty())
        memoryDataSource.saveItems(testItems)
        assert(memoryDataSource.getItems().containsAll(testItems))
    }

    @Test
    fun getsItemFromMemory() = runBlockingTest {
        memoryDataSource.saveItems(testItems)
        assert(memoryDataSource.getItem(1) != null)
    }
}