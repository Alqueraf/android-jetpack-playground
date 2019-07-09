package com.alexqueudot.android.data.repository.items.datasource.remote.api

import com.alexqueudot.android.data.model.Item
import com.google.gson.annotations.SerializedName

/**
 * Created by alex on 2019-05-20.
 */

data class ApiItem (
    @SerializedName("question_id")
    val id: Int,
    val title: String?,
    @SerializedName("link")
    val url: String?,
    val score: Int?,
    @SerializedName("is_answered")
    val isAnswered: Boolean?
) {
    fun toItem(): Item {
        return Item(id, title, url, score ?: 0, isAnswered ?: false)
    }
}

data class ApiItemsResponse (
    val items: List<ApiItem>?
)