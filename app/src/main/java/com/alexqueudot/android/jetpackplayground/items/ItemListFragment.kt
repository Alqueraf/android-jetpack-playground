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
import timber.log.Timber


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
//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                    super.onScrolled(recyclerView, dx, dy)
//                    val visibleItemCount = layoutManager?.childCount ?: 0
//                    val totalItemCount = layoutManager?.itemCount ?: 0
//                    val firstVisibleItemPosition =
//                        (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0
//                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && dy > 0) {
//                        viewModel.loadNextData()
//                        Timber.i("Loading more items")
//                    }
//                }
//            })
        }

        // Observe Data
        viewModel.items.observe(viewLifecycleOwner, Observer {
            recyclerview.visibility = VISIBLE
            Timber.i("Got $it items")
            itemsAdapter.submitList(it)
        })
        viewModel.loading.observe(viewLifecycleOwner, Observer { swipeRefreshLayout.isRefreshing = it })
//        viewModel.state.observe(viewLifecycleOwner, Observer {
//            // Update UI
//            when (it) {
//                is Available -> {
//                    recyclerview.visibility = VISIBLE
//                    noInternetView.visibility = GONE
//                    Timber.i("Got items ${it.items}")
//                    itemsAdapter.submitList(it.items)
//                }
//                is Unavailable -> {
//                    recyclerview.visibility = GONE
//                    // Handle errors
//                    it.reason?.let { error ->
//                        when (error) {
//                            is NetworkUnavailable -> {
//                                noInternetView.visibility = VISIBLE
//                            }
//                        }
//                    }
//                }
//            }
//        })
        // Observer Error Events
        viewModel.errorEvents.observe(viewLifecycleOwner, Observer {
            view?.let { view ->
                 when (it) {
                    is Blacklisted -> Snackbar.make(view, R.string.error_message_blacklisted, Snackbar.LENGTH_SHORT).show()
                    is NetworkUnavailable -> Snackbar.make(view, R.string.error_message_internet, Snackbar.LENGTH_SHORT).show()
                    else -> Snackbar.make(view, R.string.error_message_generic, Snackbar.LENGTH_SHORT).show()
                }

            }
        })

        // Listeners
        swipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        savedInstanceState?.let {
//            // Do nothing
//        } ?: viewModel.loadData()

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
