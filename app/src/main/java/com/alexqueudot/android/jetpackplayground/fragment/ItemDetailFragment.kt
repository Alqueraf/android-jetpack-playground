package com.alexqueudot.android.jetpackplayground.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alexqueudot.android.data.repository.items.error.ItemsError
import com.alexqueudot.android.jetpackplayground.R
import com.alexqueudot.android.jetpackplayground.viewmodel.ItemDetailViewModel
import kotlinx.android.synthetic.main.item_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemDetailFragment : BaseFragment() {

    private val viewModel by viewModel<ItemDetailViewModel>()
    private val args: ItemDetailFragmentArgs by navArgs()

    private fun initUI() {
        // Observe Data
        viewModel.item.observe(viewLifecycleOwner, Observer {
            title.text = it.title
            text.text = it.url
        })
        // Observe errors
        viewModel.errors.observe(this, Observer {
            if (!handleError(it)) {
                when (it) {
                    is ItemsError.NotFound -> {
                        // TODO: Notify user item is unavailable
                        findNavController().navigateUp()
                    }
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            // Do nothing
        } ?: viewModel.refreshData(args.itemId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    companion object {
        fun newInstance() = ItemDetailFragment()
    }

}
