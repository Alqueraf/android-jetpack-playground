package com.alexqueudot.android.jetpackplayground.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.alexqueudot.android.jetpackplayground.R
import com.alexqueudot.android.jetpackplayground.viewmodel.ItemDetailViewModel
import kotlinx.android.synthetic.main.item_detail_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemDetailFragment : Fragment() {

    private val viewModel by viewModel<ItemDetailViewModel>()
    private val args: ItemDetailFragmentArgs by navArgs()

    private fun initUI() {
        viewModel.item.observe(viewLifecycleOwner,
            Observer {
                title.text = it.title
                text.text = it.url
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel =
//            ViewModelProviders.of(this, ItemDetailViewModelFactory(args.itemId)).get(ItemDetailViewModel::class.java)
        viewModel.refreshData(args.itemId)
        initUI()
    }

    companion object {
        fun newInstance() = ItemDetailFragment()
    }

}
