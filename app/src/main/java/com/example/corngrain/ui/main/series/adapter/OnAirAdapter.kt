package com.example.corngrain.ui.main.series.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.navigation.Navigation
import androidx.viewpager.widget.PagerAdapter
import com.example.corngrain.R
import com.example.corngrain.data.network.response.series.SerieCurrentlyShowing
import com.example.corngrain.ui.main.series.SeriesDirections
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import kotlinx.android.synthetic.main.item_inshow_serie.view.*

class OnAirAdapter(private val entries: List<SerieCurrentlyShowing.Result>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater: LayoutInflater =
            container.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val item = inflater.inflate(R.layout.item_inshow_serie, container, false)

        item.setOnClickListener { view ->
            toSerieDetailScreen(entries[position].id, view)
        }


        val img = item.findViewById<ImageView>(R.id.in_show_img)
        val rating = item.findViewById<TextView>(R.id.on_air_rating)
        val desc = item.findViewById<TextView>(R.id.on_air_desc)

        desc.text = entries.get(position).overview
        rating.text = entries.get(position).voteAverage.toString()
        GlideApp.with(container.context)
            .load(BASE_IMG_URL + entries.get(position).posterPath)
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
    private fun toSerieDetailScreen(id: Int, view: View) {
        val action = SeriesDirections.serieDetailAction(id)
        Navigation.findNavController(view).navigate(action)
    }
}