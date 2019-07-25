package com.alexqueudot.android.jetpackplayground.items.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.alexqueudot.android.jetpackplayground.R
import com.alexqueudot.android.jetpackplayground.base.BaseFragment
import kotlinx.android.synthetic.main.item_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemDetailFragment : BaseFragment() {

    override fun getLayoutId() = R.layout.item_detail_fragment

    private val viewModel by viewModel<ItemDetailViewModel>()
    private val args: ItemDetailFragmentArgs by navArgs()

    private fun initUI() {
        // Observe Data
        viewModel.item.observe(viewLifecycleOwner, Observer {
            title.text = it.name
            text.text = it.species
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
        initUI()
    }

    companion object {
        fun newInstance() = ItemDetailFragment()
    }

}
