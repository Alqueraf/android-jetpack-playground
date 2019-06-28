package com.alexqueudot.android.data

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.data.di.data
import io.reactivex.observers.TestObserver
import org.junit.Test

/**
 * Created by alex on 2019-06-28.
 */

class ApiDataSourceUnitTest {

    private val apiDataSource = data().apiDataSource

    @Test
    fun getsItemsFromApi() {
        val testSubscriber = TestObserver<List<Item>>()
        apiDataSource.getItems().subscribe(testSubscriber)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue {
            it.count() > 0
        }
    }
}