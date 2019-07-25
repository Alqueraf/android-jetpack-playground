package com.alexqueudot.android.jetpackplayground.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.FragmentNavigator
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.repository.items.ItemsRepository
import com.alexqueudot.android.data.repository.items.error.ItemsError
import com.alexqueudot.android.data.repository.items.error.NetworkUnavailable
import com.alexqueudot.android.data.result.Failure
import com.alexqueudot.android.data.result.Success
import com.alexqueudot.android.jetpackplayground.navigation.ItemsNavigator
import com.alexqueudot.android.jetpackplayground.utils.SingleLiveEvent
import com.alexqueudot.android.jetpackplayground.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemListViewModel(private val itemsRepository: ItemsRepository, private val navigator: ItemsNavigator) :
    BaseViewModel() {

    val state: MutableLiveData<ItemListState> = MutableLiveData()
    val errorEvents = SingleLiveEvent<ItemsError>()

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
                    (itemsResponse.error as? ItemsError)?.let {
                        when (it) {
                            is NetworkUnavailable -> state.postValue(Unavailable(reason = NetworkUnavailable))
                            else -> {
                                state.postValue(Unavailable())
                                errorEvents.postValue(it)
                            }
                        }
                    }
                    state.postValue(Unavailable(itemsResponse.error as? ItemsError))
                }
            }
        }
    }

    fun itemSelected(item: Item, extras: FragmentNavigator.Extras?) {
        navigator.showDetail(item.id, extras)
    }
}
