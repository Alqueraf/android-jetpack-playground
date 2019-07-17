package com.alexqueudot.android.jetpackplayground.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.repository.items.ItemsRepository
import com.alexqueudot.android.data.repository.items.error.ItemsError
import com.alexqueudot.android.data.result.Failure
import com.alexqueudot.android.data.result.Success
import com.alexqueudot.android.jetpackplayground.navigation.ItemsNavigator
import com.alexqueudot.android.jetpackplayground.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class ItemListViewModel(private val itemsRepository: ItemsRepository, private val navigator: ItemsNavigator) :
    BaseViewModel() {

    val state: MutableLiveData<ItemListState> = MutableLiveData()

    fun loadData(refresh: Boolean = false) {
        viewModelScope.launch {
            // Set Loading State
            state.postValue(Loading)
            // Get Data
            val itemsResponse = withContext(Dispatchers.IO) { itemsRepository.getItems(refresh) }
            // Handle Response
            when (itemsResponse) {
                is Success -> state.postValue(Available(items = itemsResponse.data))
                is Failure -> {
                    state.postValue(Unavailable)
                    itemsResponse.error?.let {
                        handleError(it)
                    }
                }
            }
        }
    }

    fun itemSelected(item: Item) {
        navigator.showDetail(item.id)
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
