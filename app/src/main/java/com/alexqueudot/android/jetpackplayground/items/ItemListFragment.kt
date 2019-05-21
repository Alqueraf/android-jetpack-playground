package com.alexqueudot.android.jetpackplayground.items

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.alexqueudot.android.jetpackplayground.R
import com.alexqueudot.android.jetpackplayground.di.app
import kotlinx.android.synthetic.main.item_list_fragment.*


class ItemListFragment : Fragment() {

    private lateinit var viewModel: ItemListViewModel

    private fun initUI() {
        viewModel.items.observe(viewLifecycleOwner,
            Observer {
                testText.text = it.firstOrNull()?.title
            }
        )

        testText.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_itemListFragment_to_placeholder)
        }

        refreshButton.setOnClickListener { viewModel.refreshItems(true) }
        memoryButton.setOnClickListener { viewModel.refreshItems() }
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
        viewModel = ViewModelProviders.of(this, ItemListViewModelFactory(app().itemsRepository))
            .get(ItemListViewModel::class.java)
        initUI()
    }

    companion object {
        fun newInstance() = ItemListFragment()
    }

}
