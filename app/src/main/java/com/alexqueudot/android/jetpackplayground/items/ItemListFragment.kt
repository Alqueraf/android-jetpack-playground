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
            addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))
            adapter = itemsAdapter
        }

        // Observe Data
        viewModel.state.observe(viewLifecycleOwner, Observer {
            // Update UI
            when (it) {
                Loading -> {
                    swipeRefreshLayout.isRefreshing = true
                    recyclerview.visibility = VISIBLE
                    noInternetView.visibility = GONE
                }
                is Available -> {
                    swipeRefreshLayout.isRefreshing = false
                    recyclerview.visibility = VISIBLE
                    noInternetView.visibility = GONE

                    itemsAdapter.items = it.items
                }
                is Unavailable -> {
                    swipeRefreshLayout.isRefreshing = false
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
                Snackbar.make(view, getString(R.string.error_message_generic), Snackbar.LENGTH_SHORT).show()
            }
        })

        // Listeners
        swipeRefreshLayout.setOnRefreshListener { viewModel.loadData(true) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            // Do nothing
        } ?: viewModel.loadData(true)

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
