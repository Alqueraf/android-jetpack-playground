package com.alexqueudot.android.jetpackplayground.adapter

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by alex on 2019-05-21.
 */
class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            left =  spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}