package com.example.corngrain.ui.base

import androidx.annotation.LayoutRes
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder


abstract class BaseGroupeAdapter<T> : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            buildAdapterItemsUI(viewHolder)
        }
    }

    override fun getLayout(): Int = setGroupeAdapterLayout()

    @LayoutRes
    abstract fun setGroupeAdapterLayout(): Int

    abstract fun buildAdapterItemsUI(viewHolder: ViewHolder)


}