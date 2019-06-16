package com.example.corngrain.ui.main.series.episodes.adapter

import android.annotation.SuppressLint
import com.example.corngrain.R
import com.example.corngrain.data.network.response.series.Season
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_episdoes.*

class EpisodesAdapter(private val entry: Season.Episode) : BaseGroupeAdapter<Season.Episode>() {
    override fun setGroupeAdapterLayout(): Int = R.layout.item_episdoes

    @SuppressLint("SetTextI18n")
    override fun buildAdapterItemsUI(viewHolder: ViewHolder) {
        viewHolder.apply {
            if (entry.stillPath.isNotEmpty())
            setAdapterDisplayImage(entry.stillPath, episodes_img)
            episode_name.text = entry.name
            episodes_rating.rating = entry.voteAverage.toFloat() / 2f
            episodes_rating_count.text = "${entry.voteCount} Reviewers"
        }
    }

    override fun toGroupeAdapterItems(entries: List<Season.Episode>): List<BaseGroupeAdapter<Season.Episode>> {
        return entries.map { item ->
            EpisodesAdapter(item)
        }
    }
}