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

class ReviewsAdapter(private val entries: List<Reviews.Result>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater =
            container.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val item = inflater.inflate(R.layout.item_review, container, false)

        val name = item.findViewById<TextView>(R.id.review_user_name)
        val content = item.findViewById<TextView>(R.id.review_user_content)

        name.text = entries.get(position).author
        content.text = entries.get(position).content
Log.d("name",entries.get(position).author)
        container.addView(item)
        return item
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`

    }

    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
        container.removeView(container)
    }

    override fun getCount(): Int = entries.size

}