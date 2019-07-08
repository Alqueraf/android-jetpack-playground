package com.alexqueudot.android.jetpackplayground.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.core.usecase.GetItemListUseCase
import kotlinx.coroutines.launch
import timber.log.Timber

class ItemListViewModel(private val itemsRepository: ItemsRepository) : BaseViewModel() {

    val items: MutableLiveData<List<Item>> = MutableLiveData()

    fun refreshItems(refresh: Boolean = false) {
        viewModelScope.launch {
            val itemList = GetItemListUseCase(itemsRepository, refresh)
            items.postValue(itemList)
        }
    }

}
