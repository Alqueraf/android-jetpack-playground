package com.alexqueudot.android.jetpackplayground.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.repository.items.ItemsRepository
import com.alexqueudot.android.data.repository.items.error.ItemsError
import com.alexqueudot.android.data.result.onError
import com.alexqueudot.android.data.result.onSuccess
import com.alexqueudot.android.jetpackplayground.viewmodel.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class ItemListViewModel(private val itemsRepository: ItemsRepository) : BaseViewModel() {

    val state: MutableLiveData<ItemListState> = MutableLiveData()

    fun refreshItems(refresh: Boolean = false) {
        viewModelScope.launch {
            state.postValue(Loading)
            val itemsResponse = itemsRepository.getItems(refresh)
            // Handle Response
            itemsResponse
                .onSuccess { state.postValue(Available(items = it)) }
                .onError {
                    state.postValue(Unavailable)
                    handleError(it)
                }
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
                    true
                }
                else -> {
                    Timber.w(error, "Error was not handled")
                    false
                }
            }
        }
    }
}
