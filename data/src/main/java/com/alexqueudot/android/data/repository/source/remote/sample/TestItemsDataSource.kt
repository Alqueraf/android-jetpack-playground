package com.alexqueudot.android.data.repository.source.remote.sample

import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.data.repository.source.remote.ApiItemsResponse
import com.alexqueudot.android.data.repository.source.RemoteDataSource
import com.google.gson.Gson
import io.reactivex.Single

/**
 * Created by alex on 2019-05-21.
 */
class TestItemsDataSource: RemoteDataSource {
    override fun getItems(): Single<List<Item>> {
        return Single.just(
            Gson().fromJson(TestQuestionsHolder.questions, ApiItemsResponse::class.java).items?.map { it.toItem() }
        )
    }
}