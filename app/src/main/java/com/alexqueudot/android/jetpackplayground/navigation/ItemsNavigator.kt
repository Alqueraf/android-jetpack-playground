package com.alexqueudot.android.jetpackplayground.navigation

import androidx.navigation.NavController
import com.alexqueudot.android.jetpackplayground.items.ItemListFragmentDirections

/**
 * Created by alex on 2019-07-17.
 */

class ItemsNavigator(private val navController: NavController) {

    fun showDetail(actionId: Int) {
        val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(actionId)
        navController.navigate(action)
    }

}