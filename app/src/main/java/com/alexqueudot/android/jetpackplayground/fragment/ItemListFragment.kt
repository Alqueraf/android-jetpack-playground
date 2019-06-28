package com.alexqueudot.android.jetpackplayground.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexqueudot.android.core.entity.Item
import com.alexqueudot.android.jetpackplayground.R
import com.alexqueudot.android.jetpackplayground.adapter.ItemsAdapter
import com.alexqueudot.android.jetpackplayground.adapter.MarginItemDecoration
import com.alexqueudot.android.jetpackplayground.adapter.OnItemClickListener
import com.alexqueudot.android.jetpackplayground.viewmodel.ItemListViewModel
import kotlinx.android.synthetic.main.item_list_fragment.*


class ItemListFragment : Fragment() {

    private lateinit var viewModel: ItemListViewModel

    private fun onListItemClick(item: Item) {
        // Navigate to Detail
        val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment().setItemId(item.id)
        findNavController().navigate(action)
    }

    private fun initUI() {
        // Init Recyclerview + Adapter
        recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerview.addItemDecoration(MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.margin)))
        val adapter = ItemsAdapter(itemClickListener = object : OnItemClickListener<Item> {
            override fun onItemClick(item: Item) = onListItemClick(item)
        })
        recyclerview.adapter = adapter

        // Observe Data
        viewModel.items.observe(viewLifecycleOwner,
            Observer {
                // Update UI
                adapter.items = it
                if (it.isNotEmpty()) recyclerview.scrollToPosition(0)
                swipeRefreshLayout.isRefreshing = false
            }
        )

        // Listeners
        swipeRefreshLayout.setOnRefreshListener { viewModel.refreshItems(true) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ItemListViewModel::class.java)
        initUI()
    }

    companion object {
        fun newInstance() = ItemListFragment()
    }

}
