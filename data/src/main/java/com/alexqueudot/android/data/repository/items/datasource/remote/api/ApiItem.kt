package com.alexqueudot.android.data.repository.items.datasource.remote.api

import com.alexqueudot.android.data.model.Item
import com.google.gson.annotations.SerializedName

/**
 * Created by alex on 2019-05-20.
 */

data class ApiItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("image")
    val image: String?
) {
    fun toItem(): Item {
        return Item(id, name ?: "", status ?: "", species ?: "", type ?: "", gender ?: "", image ?: "")
    }
}