package com.example.corngrain.ui.main.people

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.corngrain.R
import com.example.corngrain.ui.base.ScopedFragment
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
        val job = viewModel.fetchPersons.await()
        job.observeForever { personsData ->
            if (sample_txt_person != null)
                sample_txt_person.text = personsData.toString()
        }
    }

}
