package com.alexqueudot.android.jetpackplayground.navigation

import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.alexqueudot.android.jetpackplayground.items.ItemListFragmentDirections

/**
 * Created by alex on 2019-07-17.
 */

class ItemsNavigator(private val navController: NavController) {

    fun showDetail(actionId: Int, extras: FragmentNavigator.Extras? = null) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(actionId)
        extras?.let { navController.navigate(action, extras) } ?: navController.navigate(action)
    }

}