package com.alexqueudot.android.jetpackplayground.items

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
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

    private val paginatedResult = itemsRepository.getItemsPaginated(20)

    val items = paginatedResult.pagedList
    val loading = paginatedResult.refreshing
    val errorEvents = paginatedResult.dataError

    val state = MutableLiveData<ItemListState>()
//    val loading = MutableLiveData<Boolean>()
//    val errorEvents = SingleLiveEvent<ItemsError>()

    fun refresh() {
        paginatedResult.refresh.invoke()
    }

//    fun loadData(page: Int? = null) {
//        viewModelScope.launch {
//            // Set Loading State
//            loading.postValue(true)
//            // Get Data
//            val itemsResponse = withContext(Dispatchers.IO) { itemsRepository.getItems(page) }
//            // Handle Response
//            when (itemsResponse) {
//                is Success -> {
//                    page?.let { page ->
//                        (state.value as? Available)?.let { value ->
//                            state.postValue(Available(items = ArrayList(value.items).apply { addAll(itemsResponse.data) }))
//                        } ?: state.postValue(Available(items = itemsResponse.data))
//                    } ?: state.postValue(Available(items = itemsResponse.data))
//                }
//                is Failure -> {
//                    (itemsResponse.error as? ItemsError)?.let {
//                        when (it) {
//                            is NetworkUnavailable -> state.postValue(Unavailable(reason = NetworkUnavailable))
//                            else -> {
//                                state.postValue(Unavailable())
//                                errorEvents.postValue(it)
//                            }
//                        }
//                    }
//                    state.postValue(Unavailable(itemsResponse.error as? ItemsError))
//                }
//            }
//            loading.postValue(false)
//        }
//    }

    fun itemSelected(item: Item, extras: FragmentNavigator.Extras?) {
        navigator.showDetail(item.id, extras)
    }
}
