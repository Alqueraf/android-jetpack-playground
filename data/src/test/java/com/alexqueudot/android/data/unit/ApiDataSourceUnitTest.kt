package com.alexqueudot.android.data.unit

import com.alexqueudot.android.data.unit.di.testModule
import com.alexqueudot.android.data.repository.items.datasource.remote.api.ApiItemsDataSource
import com.alexqueudot.android.data.result.Success
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
class ApiDataSourceUnitTest : KoinTest {

    private val apiDataSource by inject<ApiItemsDataSource>()

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
    fun getsItemsFromApi() = runBlockingTest {
        // Result
        val result = apiDataSource.getItems()
        assert(result is Success)
        // Items
        val items = (result as Success).data
        assert(items.isNotEmpty())
        // Item
        val firstItem = items.first()
        assert(firstItem.id == 6873175)
        assert(firstItem.title == "Auditing with Spring Data JPA")
        assert(firstItem.url == "https://stackoverflow.com/questions/6873175/auditing-with-spring-data-jpa")
        assert(firstItem.score == 1)
        assert(firstItem.isAnswered == true)
    }

    @Test
    fun getItemFromApi() = runBlockingTest {
        // Result
        val result = apiDataSource.getItem(6873175)
        assert(result is Success)
        // Items
        val item = (result as Success).data
        assert(item.id == 6873175)
        assert(item.title == "Auditing with Spring Data JPA")
        assert(item.url == "https://stackoverflow.com/questions/6873175/auditing-with-spring-data-jpa")
        assert(item.score == 1)
        assert(item.isAnswered == true)
    }

}