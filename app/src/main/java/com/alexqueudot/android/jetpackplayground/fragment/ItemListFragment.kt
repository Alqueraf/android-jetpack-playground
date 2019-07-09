package com.alexqueudot.android.jetpackplayground.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.data.repository.items.error.ItemsError
import com.alexqueudot.android.jetpackplayground.R
import com.alexqueudot.android.jetpackplayground.adapter.ItemsAdapter
import com.alexqueudot.android.jetpackplayground.adapter.MarginItemDecoration
import com.alexqueudot.android.jetpackplayground.adapter.OnItemClickListener
import com.alexqueudot.android.jetpackplayground.viewmodel.ItemListViewModel
import kotlinx.android.synthetic.main.item_list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ItemListFragment : BaseFragment() {

    private val viewModel by viewModel<ItemListViewModel>()
    private val adapter by lazy {
        ItemsAdapter(itemClickListener = object : OnItemClickListener<Item> {
            override fun onItemClick(item: Item) = onListItemClick(item)
        })
    }

    private fun onListItemClick(item: Item) {
        // Navigate to Detail
        val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(item.id)
        navigate(action)
    }

    private fun initUI() {
        // Init Recyclerview + Adapter
        recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerview.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))
        recyclerview.adapter = adapter

        // Observe Data
        viewModel.items.observe(this, Observer {
            // Update UI
            adapter.items = it
            swipeRefreshLayout.isRefreshing = false
        })
        // Observe errors
        viewModel.errors.observe(this, Observer {
            if (!handleError(it)) {
                when (it) {
                    is ItemsError.Blacklisted -> {
                        // TODO: Notify user he has been blacklisted
                    }
                }
            }
            swipeRefreshLayout.isRefreshing = false
        })

        // Listeners
        swipeRefreshLayout.setOnRefreshListener { viewModel.refreshItems(true) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            // Do nothing
        } ?: viewModel.refreshItems(true)

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
