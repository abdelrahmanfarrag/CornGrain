package com.example.corngrain.ui.main.trending.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.navigation.Navigation
import androidx.viewpager.widget.PagerAdapter
import com.example.corngrain.R
import com.example.corngrain.data.network.response.trending.Trending
import com.example.corngrain.ui.main.trending.TrendingFragmentDirections
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp

class TrendingPagerAdapter(val entries: List<Trending.Result>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater =
            container.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val item = inflater.inflate(R.layout.item_trending_movies, container, false)

        item.setOnClickListener { viewClicked ->
            toDetailsScreen(entries[position].id, viewClicked)
        }
        val img = item.findViewById<ImageView>(R.id.pager_img_item)

        GlideApp.with(container.context)
            .load(BASE_IMG_URL + entries[position].posterPath)
            .placeholder(R.drawable.ic_placeholder)
            .into(img)

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

    private fun toDetailsScreen(id: Int, viewClicked: View) {
        val action = TrendingFragmentDirections.trendingToMovieDetail(id)
        Navigation.findNavController(viewClicked).navigate(action)
    }

}