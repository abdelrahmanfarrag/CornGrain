package com.example.corngrain.ui.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.corngrain.R
import com.example.corngrain.data.bus.NoNetworkBus
import com.example.corngrain.ui.main.MainActivity
import com.example.corngrain.utilities.EventBus
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), KodeinAware, CoroutineScope {

    override val kodein: Kodein by closestKodein()
    private lateinit var job: Job
    var gridRecyclerViewRowCount: Int = 0
    private val handler = CoroutineExceptionHandler { _, _ ->
        EventBus.post(NoNetworkBus())
    }
    override val coroutineContext: CoroutineContext
        get() = job + handler + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridRecyclerViewRowCount = 4
        } else {
            gridRecyclerViewRowCount = 3
        }

        job = Job()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(setFragmentLayout(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindFragmentUI()
        (context as MainActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    @LayoutRes
    abstract fun setFragmentLayout(): Int

    abstract fun bindFragmentUI()

}