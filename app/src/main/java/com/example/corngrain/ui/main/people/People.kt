package com.example.corngrain.ui.main.people

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.corngrain.R
import com.example.corngrain.data.network.response.people.PersonDetail
import com.example.corngrain.data.network.response.people.PopularPersons
import com.example.corngrain.ui.base.ScopedFragment
import com.example.corngrain.ui.main.people.adapter.PopularPersonsAdapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.people_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class People : ScopedFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()

    private val factory by instance<PeopleViewmodelFactory>()

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
        }
        viewModel.fetchPersonDetail(1245).observeForever { personDetail ->
            Log.d("tse", personDetail.biography)
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
