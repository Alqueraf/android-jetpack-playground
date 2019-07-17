package com.alexqueudot.android.jetpackplayground.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexqueudot.android.data.model.Item
import com.alexqueudot.android.jetpackplayground.R
import kotlinx.android.synthetic.main.row_item.view.*

/**
 * Created by alex on 2019-05-21.
 */
class ItemsAdapter(items: List<Item>? = null, val itemClickListener: ((Item) -> Unit)?) :
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
        items?.getOrNull(position)?.let { item ->
            holder.textView.text = item.title
            holder.bind(item, itemClickListener)
        }
    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container = itemView
        val textView = itemView.text

        fun bind(item: Item, itemClickListener: ((Item) -> Unit)?) {
            container.setOnClickListener {
                itemClickListener?.invoke(item)
            }
        }
    }
}