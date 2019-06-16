package com.example.corngrain.ui.main.people

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.corngrain.R
import com.example.corngrain.data.network.response.people.PersonMovies
import com.example.corngrain.data.network.response.people.PopularPersons
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.people.adapter.PersonMovieAdapter
import com.example.corngrain.ui.main.people.adapter.PopularPersonsAdapter
import com.example.corngrain.utilities.BASE_IMG_URL
import com.example.corngrain.utilities.GlideApp
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
            settingNormalRecyclerViewConfigs(
                this@People.context,
                personsData.results.toAdapter(),
                persons_list,
                RecyclerView.HORIZONTAL
            )
            generatedId = personsData.results[generateRandomizedNumber()].id
        }
        if (generatedId != null)
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
        if (generatedId != null)
            viewModel.fetchPersonMovies(generatedId!!).observeForever { personMovies ->
                settingNormalRecyclerViewConfigs(
                    this@People.context,
                    personMovies.cast.toPersonMoviesAdapter(),
                    picked_detail_list,
                    RecyclerView.VERTICAL,
                    true,
                    3
                )
            }
    }


    private fun List<PersonMovies.Cast>.toPersonMoviesAdapter(): List<PersonMovieAdapter> {
        return this.map {
            PersonMovieAdapter(it)
        }
    }

    private fun List<PopularPersons.Result>.toAdapter(): List<PopularPersonsAdapter> {
        return this.map { item ->
            PopularPersonsAdapter(item)
        }
    }


}
