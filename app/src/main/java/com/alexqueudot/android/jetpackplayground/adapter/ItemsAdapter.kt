package com.alexqueudot.android.jetpackplayground.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.jetpackplayground.R
import com.alexqueudot.android.jetpackplayground.utils.itemImageTransition
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_item.view.*

/**
 * Created by alex on 2019-05-21.
 */
class ItemsAdapter(items: List<Item>? = null, val itemClickListener: ((Item, FragmentNavigator.Extras?) -> Unit)?) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    var items: List<Item>? = items
        set(value) {
            if (field != value) {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        items?.getOrNull(position)?.let { holder.bind(it, itemClickListener) }
    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container = itemView
        private val avatar = itemView.avatar
        private val title = itemView.title
        private val subtitle = itemView.subtitle

        fun bind(item: Item, itemClickListener: ((Item, FragmentNavigator.Extras?) -> Unit)?) {
            // Views
            Glide.with(avatar).load(item.image).fitCenter().into(avatar)
            avatar.transitionName = item.name
            title.text = item.name
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
    }
}