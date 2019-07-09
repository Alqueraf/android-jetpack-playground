package com.alexqueudot.android.jetpackplayground.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.repository.items.ItemsRepository
import com.alexqueudot.android.data.repository.items.error.ItemsError
import com.alexqueudot.android.data.result.onError
import com.alexqueudot.android.data.result.onSuccess
import kotlinx.coroutines.launch

class ItemListViewModel(private val itemsRepository: ItemsRepository) : BaseViewModel() {

    val items: MutableLiveData<List<Item>> = MutableLiveData()

    fun refreshItems(refresh: Boolean = false) {
        viewModelScope.launch {
            // TODO: state: loading
            val itemsResponse = itemsRepository.getItems(refresh)
            // Handle Response
            itemsResponse
                .onSuccess { items.postValue(it) }
                .onError { handleError(it) }
        }
    }

    override fun handleError(error: Throwable): Boolean {
        return if (super.handleError(error)) {
            true
        } else {
            // Handle specific errors
            when (error) {
                is ItemsError.Blacklisted -> {
                    errors.postValue(ItemsError.Blacklisted)
                }
            }
            true
        }
    }
}
