package com.example.corngrain.ui.main.people.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.corngrain.R
import com.example.corngrain.data.network.response.people.PopularPersons
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.popular_person_item.*

class PopularPersonsAdapter(private val entries: PopularPersons.Result) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            popular_person_name.text = entries.name
            personImage()
            setRecycler(entries.knownFor.toAdapter())
        }
    }

    override fun getLayout(): Int = R.layout.popular_person_item

    private fun ViewHolder.personImage() {
        GlideApp.with(this.containerView)
            .load(BASE_IMG_URL + entries.profilePath)
            .into(person_img)
    }

    private fun List<PopularPersons.Result.KnownFor>.toAdapter(): List<KnownForAdapter> {
        return this.map { item ->
            KnownForAdapter(item)
        }
    }

    private fun ViewHolder.setRecycler(entries: List<KnownForAdapter>) {
        val knownAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(entries)
        }
        known_list.apply {
            layoutManager = GridLayoutManager(this.context,2,RecyclerView.VERTICAL,false)
            adapter = knownAdapter
        }
    }
}