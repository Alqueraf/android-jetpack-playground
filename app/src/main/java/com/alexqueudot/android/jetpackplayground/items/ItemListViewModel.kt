package com.alexqueudot.android.jetpackplayground.items

import androidx.lifecycle.*
import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.core.repository.ItemsRepository
import com.alexqueudot.android.core.usecase.GetItemsUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ItemListViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {
    private val disposables = CompositeDisposable()

    val items: MutableLiveData<List<Item>> = MutableLiveData()
    init {
        refreshItems()
    }

    fun refreshItems(refresh: Boolean = false) {
        val disposable = GetItemsUseCase(itemsRepository, refresh)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(items::postValue)
        disposables.add(disposable)

    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

//    val item: LiveData<Item?> = LiveDataReactiveStreams.fromPublisher(itemsRepository.getItems().toFlowable().map { it.firstOrNull() })


}

class ItemListViewModelFactory(private val itemsRepository: ItemsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemListViewModel(itemsRepository) as T
    }

}
