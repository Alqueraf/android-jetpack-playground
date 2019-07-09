package com.alexqueudot.android.jetpackplayground.items.detail

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

class ItemDetailViewModel(private val itemsRepository: ItemsRepository): BaseViewModel() {

    val item = MutableLiveData<Item>()

    fun refreshData(itemId: Int) {
//        val disposable = GetItemDetailsUseCase(itemsRepository, itemId)
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe(item::postValue) { onError(it) }
//        disposables.add(disposable)

        viewModelScope.launch {
            itemsRepository.getItem(itemId)
                .onSuccess { item.postValue(it) }
                .onError { handleError(it) }

        }
    }

    override fun handleError(error: Throwable): Boolean {
        return if (super.handleError(error)) {
            true
        } else {
            // Handle specific errors
            when (error) {
                is ItemsError.NotFound -> {
                    errors.postValue(ItemsError.NotFound)
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

//
//class ItemDetailViewModelFactory(private val itemId: Int) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return ItemDetailViewModel(itemsRepository = app().itemsRepository, itemId = itemId) as T
//    }
//
//}