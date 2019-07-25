package com.alexqueudot.android.jetpackplayground.items.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.alexqueudot.android.jetpackplayground.R
import com.alexqueudot.android.jetpackplayground.base.BaseFragment
import com.alexqueudot.android.jetpackplayground.utils.itemImageTransition
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemDetailFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.item_detail_fragment

    private val viewModel by viewModel<ItemDetailViewModel>()
    private val args: ItemDetailFragmentArgs by navArgs()

    private var handler = Handler(Looper.getMainLooper())

    private fun initUI() {
        // Observe Data
        viewModel.item.observe(viewLifecycleOwner, Observer {
            context?.let { context ->
                // Avatar
                avatar.transitionName = itemImageTransition(it.id)
                Glide.with(context)
                    .addDefaultRequestListener(imageTransitionRequestListener)
                    .load(it.image).centerCrop().into(avatar)
                // Title
                title.text = it.name
                // Description
                description.text = it.species

                (activity as? AppCompatActivity)?.supportActionBar?.title = it.name
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            // Do nothing
        } ?: viewModel.loadData(args.itemId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Handle Transitions
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        postponeEnterTransition()
        // Timeout if Glide loading takes more time
        handler.postDelayed(500) {
            startPostponedEnterTransition()
        }

        initUI()
    }

    companion object {
        fun newInstance() = ItemDetailFragment()
    }

}
