package com.example.corngrain.ui.base

import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.example.corngrain.R
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
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

    abstract fun toGroupeAdapterItems(entries: List<T>): List<BaseGroupeAdapter<T>>

    protected fun ViewHolder.setAdapterDisplayImage(imageUrl: String?, loadIn: ImageView) {
        if (imageUrl!=null) {
            GlideApp.with(this.containerView)
                .load(BASE_IMG_URL + imageUrl)
                .placeholder(R.drawable.ic_placeholder)
                .into(loadIn)
        }
    }


}