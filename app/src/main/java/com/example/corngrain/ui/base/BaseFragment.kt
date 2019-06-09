package com.example.corngrain.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.corngrain.R
import com.example.corngrain.ui.main.MainActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein

abstract class BaseFragment : Fragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

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