package com.alexqueudot.android.jetpackplayground.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.alexqueudot.android.data.result.DataError
import com.alexqueudot.android.jetpackplayground.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Created by alex on 2019-05-21.
 */

open class BaseViewModel : ViewModel() {
    private val disposables = CompositeDisposable()
    val errors = SingleLiveEvent<Throwable>()

    @CallSuper
    protected open fun handleError(error: Throwable): Boolean {
        when (error) {
            is DataError.NetworkUnavailable -> {
                errors.postValue(error)
                return true
            }
            is DataError.Unauthorized -> {
                // TODO: Silent login?
                errors.postValue(error)
                return true
            }
            is DataError.Unknown -> {
                Timber.i("Unknown error from itemsResponse: ${error.message}")
                errors.postValue(error)
                return true
            }
            else -> return false
        }
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}