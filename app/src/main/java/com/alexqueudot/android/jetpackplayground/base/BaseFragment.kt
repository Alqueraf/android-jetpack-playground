package com.alexqueudot.android.jetpackplayground.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.alexqueudot.android.data.result.DataError
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import timber.log.Timber

/**
 * Created by alex on 2019-07-09.
 */

abstract class BaseFragment : Fragment() {
    abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    // Hero Image Transition with Glide
    val imageTransitionRequestListener: RequestListener<Any> = object : RequestListener<Any> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Any>?,
            isFirstResource: Boolean
        ): Boolean {
            startPostponedEnterTransition()
            return false
        }

        override fun onResourceReady(
            resource: Any?,
            model: Any?,
            target: Target<Any>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            Timber.i("++ onResourceReady ++")
            startPostponedEnterTransition()
            return false
        }
    }
}
