package com.alexqueudot.android.jetpackplayground.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.alexqueudot.android.data.result.DataError
import timber.log.Timber

/**
 * Created by alex on 2019-07-09.
 */

abstract class BaseFragment : Fragment() {

    fun handleError(error: Throwable): Boolean {
        return when (error) {
            is DataError.NetworkUnavailable -> {
                // TODO: Show No network dialog
                Timber.i("++ Network connection UNAVAILABLE ++")
                true
            }
            is DataError.Unauthorized -> {
                // TODO: Navigate to login UI
                true
            }
            else -> false
        }
    }

    fun navigate(action: NavDirections) {
        try {
            findNavController().navigate(action)
        } catch (e: IllegalArgumentException) {
            Timber.w(e, "IllegalArgumentException while navigating to $action")
        }
    }
}