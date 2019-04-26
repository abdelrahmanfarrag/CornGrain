package com.example.corngrain.ui.main.people

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.corngrain.R
import com.example.corngrain.data.network.response.people.PersonDetail
import com.example.corngrain.data.network.response.people.PersonMovies
import com.example.corngrain.data.network.response.people.PopularPersons
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.movies.adapters.BASE_IMG_URL
import com.example.corngrain.ui.main.people.adapter.PersonMovieAdapter
import com.example.corngrain.ui.main.people.adapter.PopularPersonsAdapter
import com.example.corngrain.utilities.GlideApp
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.people_fragment.*
import kotlinx.android.synthetic.main.picked_from_stars.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class People : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private val factory by instance<PeopleViewmodelFactory>()
    private var generatedId: Int? = null

    companion object {
        fun newInstance() = People()
    }

    private lateinit var viewModel: PeopleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.people_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(PeopleViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val persons = viewModel.fetchPersons.await()
        persons.observeForever { personsData ->
            initPopularPersonsRecycler(personsData.results.toAdapter())
            generatedId = personsData.results[generateRandomizedNumber()].id
        }
        if (generatedId!=null)
        viewModel.fetchPersonDetail(generatedId!!).observeForever { personDetail ->
            if (picked_star_img != null)
                GlideApp.with(this@People.context!!)
                    .load(BASE_IMG_URL + personDetail.profilePath)
                    .into(picked_star_img)
            if (picked_overview_txt != null)
                picked_overview_txt.text = personDetail.biography
            if (picked_star_name != null)
                picked_star_name.text = personDetail.name
            if (picked_type != null)
                picked_type.text = personDetail.knownForDepartment
            if (picked_dob != null)
                picked_dob.text = personDetail.birthday
            if (picked_born != null)
                picked_born.text = personDetail.placeOfBirth
        }
        if (generatedId!=null)
        viewModel.fetchPersonMovies(generatedId!!).observeForever { personMovies ->
            instantiatePersonsMoviesRecycler(personMovies.cast.toPesonMoviesAdapter())
        }
    }

    private fun generateRandomizedNumber(): Int {
        return (0..19).random()
    }

    private fun List<PersonMovies.Cast>.toPesonMoviesAdapter(): List<PersonMovieAdapter> {
        return this.map {
            PersonMovieAdapter(it)
        }
    }

    private fun instantiatePersonsMoviesRecycler(entries: List<PersonMovieAdapter>) {
        val group = GroupAdapter<ViewHolder>().apply {
            addAll(entries)
        }
        if (picked_detail_list != null)
            picked_detail_list.apply {
                layoutManager = GridLayoutManager(this.context, 3, RecyclerView.VERTICAL, false)
                adapter = group
            }
    }


    private fun List<PopularPersons.Result>.toAdapter(): List<PopularPersonsAdapter> {
        return this.map { item ->
            PopularPersonsAdapter(item)
        }
    }

    private fun initPopularPersonsRecycler(items: List<PopularPersonsAdapter>) {
        val groupieAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }
        if (persons_list != null) {
            persons_list.apply {
                layoutManager =
                    LinearLayoutManager(this@People.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = groupieAdapter
            }
        }
    }
}
