package com.alexqueudot.android.jetpackplayground.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexqueudot.android.data.repository.items.error.ItemsError
import com.alexqueudot.android.jetpackplayground.R
import com.alexqueudot.android.jetpackplayground.adapter.ItemsAdapter
import com.alexqueudot.android.jetpackplayground.adapter.MarginItemDecoration
import com.alexqueudot.android.jetpackplayground.fragment.BaseFragment
import com.alexqueudot.android.jetpackplayground.navigation.ItemsNavigator
import kotlinx.android.synthetic.main.item_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ItemListFragment : BaseFragment() {

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
        viewModel.state.observe(this, Observer {
            // Update UI
            when (it) {
                Loading -> {
                    swipeRefreshLayout.isRefreshing = true
                }
                is Available -> {
                    swipeRefreshLayout.isRefreshing = false
                    itemsAdapter.items = it.items
                }
                Unavailable -> {
                    swipeRefreshLayout.isRefreshing = false
                    // TODO: Show Unavailable UI
                }
            }
        })
        // Observe errors
        viewModel.errors.observe(this, Observer {
            if (!handleError(it)) {
                when (it) {
                    is ItemsError.Blacklisted -> {
                        // TODO: Notify user he has been blacklisted and cannot access list of items
                    }
                }
            }
            swipeRefreshLayout.isRefreshing = false
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.item_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    companion object {
        fun newInstance() = ItemListFragment()
    }

}
