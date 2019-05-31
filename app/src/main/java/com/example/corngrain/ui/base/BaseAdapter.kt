package com.example.corngrain.ui.base

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

open class BaseAdapter<T>(val item: T, private val layout: Int) : Item() {

    override fun getLayout(): Int = layout

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
        }

    }
fun <T> toAdapterItems(item:T){
}

}