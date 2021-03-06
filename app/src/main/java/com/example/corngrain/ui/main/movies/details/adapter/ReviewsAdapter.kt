package com.example.corngrain.ui.main.movies.details.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.example.corngrain.R
import com.example.corngrain.data.network.response.Reviews
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_review.*

class ReviewsAdapter(private val entries: Reviews.Result) : BaseGroupeAdapter<Reviews.Result>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.item_review

    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            review_user_name.text = entries.author
            review_user_content.text = entries.content
        }
    }

    override fun toGroupeAdapterItems(entries: List<Reviews.Result>): List<BaseGroupeAdapter<Reviews.Result>> {
        return entries.map { item ->
            ReviewsAdapter(item)
        }
    }
}