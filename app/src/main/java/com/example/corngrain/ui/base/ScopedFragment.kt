package com.example.corngrain.ui.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.rd.PageIndicatorView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.on_airtoday.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


/* ScopedFragment is used to create a local coroutine scope
 * this is useful to stop listening to data after fragment is destroyed so , we can not get a crash
 * from coroutine :D*
 * also have the common ui items setup such as viewPager auto rotate ,RecyclerView configs
 */
abstract class ScopedFragment : Fragment(), CoroutineScope {

    private lateinit var job: Job
    private var isHandleStarted: Boolean = false
    var currentPage = 0
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("coroutineException", "$exception handled !")
    }
    override val coroutineContext: CoroutineContext
        get() = job + handler+Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

    }

    fun autoPagerSlide(
        viewPager: ViewPager?,
        pagerIndicator: PageIndicatorView?,
        items: Int,
        duration: Long = 3000
    ) {
        if (viewPager != null && dots_layout != null) {
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    currentPage = position
                    pagerIndicator?.setSelected(position)
                }

            })
            if (!isHandleStarted) {
                val handler = Handler()
                handler.post(object : Runnable {
                    override fun run() {
                        if (currentPage < items) {
                            viewPager.setCurrentItem(currentPage, true)
                            handler.postDelayed(this, duration)
                            currentPage++

                        } else if (currentPage == items) {
                            currentPage = 0
                            viewPager.setCurrentItem(currentPage, true)
                            handler.postDelayed(this, duration)
                            currentPage++
                        }
                    }

                })
            }
            isHandleStarted = true
        }
    }


    fun settingNormalRecyclerViewConfigs(
        context: Context?,
        entries: List<Item>,
        view: RecyclerView?,
        orientation: Int = RecyclerView.VERTICAL
        , isGrid: Boolean = false,
        spinCount: Int = 2
    ): GroupAdapter<ViewHolder> {
        val groupieAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(entries)
        }
        view?.apply {
            if (isGrid) {
                when (orientation) {
                    RecyclerView.HORIZONTAL ->
                        this.layoutManager =
                            GridLayoutManager(
                                context,
                                spinCount,
                                RecyclerView.HORIZONTAL,
                                false
                            )
                    else ->
                        this.layoutManager =
                            GridLayoutManager(context, spinCount, RecyclerView.VERTICAL, false)

                }


            } else {
                when (orientation) {
                    RecyclerView.HORIZONTAL ->
                        this.layoutManager =
                            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

                    else -> this.layoutManager =
                        LinearLayoutManager(context, RecyclerView.VERTICAL, false)


                }
            }
            this.adapter = groupieAdapter
        }
        return groupieAdapter

    }


    fun generateRandomizedNumber(): Int {
        return (0..19).random()
    }

    override fun onStop() {
        super.onStop()
        isHandleStarted = false
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}