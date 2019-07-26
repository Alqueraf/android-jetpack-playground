package com.alexqueudot.android.jetpackplayground.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.jetpackplayground.R
import com.alexqueudot.android.jetpackplayground.utils.itemImageTransition
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_item.view.*
import timber.log.Timber

/**
 * Created by alex on 2019-05-21.
 */

class ItemsAdapter(val itemClickListener: ((Item, FragmentNavigator.Extras?) -> Unit)?) :
    PagedListAdapter<Item, ItemsAdapter.ItemViewHolder>(ItemsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        try {
            getItem(position)?.let {
                holder.bind(it, itemClickListener)
            } ?: holder.bindWithPlaceholders()
        } catch (e: IndexOutOfBoundsException) {
            Timber.w(e, "Error getting item for position $position")
        }
    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container = itemView
        private val avatar = itemView.avatar
        private val title = itemView.title
        private val subtitle = itemView.subtitle

        fun bind(item: Item, itemClickListener: ((Item, FragmentNavigator.Extras?) -> Unit)?) {
            // Views
            Glide.with(avatar).load(item.image).fitCenter().into(avatar)
            avatar.transitionName = itemImageTransition(item.id)
            title.text = String.format("%s - #%d", item.name ?: "", item.id)
            subtitle.text = item.species ?: subtitle.resources.getString(R.string.unknown_species)
            // Events
            container.setOnClickListener {
                // Transition
                val extras = FragmentNavigatorExtras(
                    avatar to itemImageTransition(item.id)
                )
                // Click
                itemClickListener?.invoke(item, extras)
            }
        }
        fun bindWithPlaceholders() {
            title.text = ""
            subtitle.text = ""
            // TODO: Placeholder image for avatar
        }
    }
}

class ItemsDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}