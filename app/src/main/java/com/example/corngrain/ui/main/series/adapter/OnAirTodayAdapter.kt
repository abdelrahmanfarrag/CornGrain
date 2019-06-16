package com.example.corngrain.ui.main.series.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.navigation.Navigation
import androidx.viewpager.widget.PagerAdapter
import com.example.corngrain.R
import com.example.corngrain.data.db.entity.series.OnAirTodayEntity
import com.example.corngrain.data.network.response.series.OnAirToday
import com.example.corngrain.ui.main.series.SeriesDirections
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp

class OnAirTodayAdapter(private val entries: OnAirToday) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater =
            container.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val item = inflater.inflate(R.layout.on_air_pager_item, container, false)
        item.setOnClickListener { view ->
            toSerieDetailScreen(entries.results[position].id, view)
        }
        val imageView = item.findViewById<ImageView>(R.id.pager_img_item)
        GlideApp.with(container.context)
            .load(BASE_IMG_URL + entries.results[position].posterPath)
            .placeholder(R.drawable.ic_placeholder)
            .into(imageView)
        container.addView(item)
        return item
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`

    }

    override fun destroyItem(@NonNull container: ViewGroup, position: Int, @NonNull `object`: Any) {
        container.removeView(container)
    }

    override fun getCount(): Int = entries.results.size
    private fun toSerieDetailScreen(id: Int, view: View) {
        val action = SeriesDirections.serieDetailAction(id)
        Navigation.findNavController(view).navigate(action)
    }
}
