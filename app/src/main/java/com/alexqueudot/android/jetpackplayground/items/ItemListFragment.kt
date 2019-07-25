package com.alexqueudot.android.jetpackplayground.items

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.alexqueudot.android.data.repository.items.error.Blacklisted
import com.alexqueudot.android.data.repository.items.error.NetworkUnavailable
import com.alexqueudot.android.jetpackplayground.R
import com.alexqueudot.android.jetpackplayground.adapter.ItemsAdapter
import com.alexqueudot.android.jetpackplayground.adapter.MarginItemDecoration
import com.alexqueudot.android.jetpackplayground.base.BaseFragment
import com.alexqueudot.android.jetpackplayground.navigation.ItemsNavigator
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ItemListFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.item_list_fragment

    private val viewModel by viewModel<ItemListViewModel> {
        parametersOf(ItemsNavigator(findNavController()))
    }
    private val itemsAdapter by lazy {
        ItemsAdapter(itemClickListener = viewModel::itemSelected)
    }

    private fun initUI() {
        // Init Recyclerview
        recyclerview.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.small_margin)))
            adapter = itemsAdapter
        }

        // Observe Data
        viewModel.loading.observe(viewLifecycleOwner, Observer { swipeRefreshLayout.isRefreshing = it })
        viewModel.state.observe(viewLifecycleOwner, Observer {
            // Update UI
            when (it) {
                is Available -> {
                    recyclerview.visibility = VISIBLE
                    noInternetView.visibility = GONE

                    itemsAdapter.submitList(it.items)
                }
                is Unavailable -> {
                    recyclerview.visibility = GONE
                    // Handle errors
                    it.reason?.let { error ->
                        when (error) {
                            is NetworkUnavailable -> {
                                noInternetView.visibility = VISIBLE
                            }
                        }
                    }
                }
            }
        })
        // Observer Error Events
        viewModel.errorEvents.observe(viewLifecycleOwner, Observer {
            view?.let { view ->
                val stringResId = when(it) {
                    is Blacklisted -> R.string.error_message_blacklisted
                    else -> R.string.error_message_generic
                }
                Snackbar.make(view, stringResId, Snackbar.LENGTH_SHORT).show()
            }
        })

        // Listeners
        swipeRefreshLayout.setOnRefreshListener { viewModel.loadData() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            // Do nothing
        } ?: viewModel.loadData()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Handle Transitions
        postponeEnterTransition()
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        recyclerview.viewTreeObserver.addOnPreDrawListener { startPostponedEnterTransition(); true }

        initUI()

    }

    companion object {
        fun newInstance() = ItemListFragment()
    }

}
