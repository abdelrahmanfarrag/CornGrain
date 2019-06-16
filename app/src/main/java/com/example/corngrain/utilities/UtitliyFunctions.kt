package com.example.corngrain.utilities

import android.content.Context
import android.os.Handler
import android.view.View
import androidx.lifecycle.LiveData
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Fade
import androidx.transition.TransitionSet
import androidx.viewpager.widget.ViewPager
import com.example.corngrain.ui.base.BaseAdapter
import com.example.corngrain.ui.base.BaseGroupeAdapter
import com.rd.PageIndicatorView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.coroutines.*
import java.lang.Runnable

const val BASE_IMG_URL = "https://image.tmdb.org/t/p/w500"

fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}

fun <T> lazyDeferredWithId(id: Int, block: suspend CoroutineScope.(Int) -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block(id)
        }
    }
}

fun <T> executeMoreClick(
    currentPage: Int,
    totalPages: Int,
    suspendBlock: suspend CoroutineScope.(Int) -> LiveData<T>
) {
    if (currentPage < totalPages) {
        GlobalScope.launch {
            suspendBlock(currentPage)
        }
    }
}

fun normalRecyclerView(
    context: Context?,
    entries: List<Item>,
    orientation: Int = RecyclerView.VERTICAL,
    view: RecyclerView?
): GroupAdapter<ViewHolder> {
    val groupeAdapter = GroupAdapter<ViewHolder>().apply {
        addAll(entries)
    }

    view?.apply {
        when (orientation) {
            RecyclerView.HORIZONTAL -> this.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            else -> this.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        }
        this.adapter = groupeAdapter
    }
    return groupeAdapter
}

/* this function is to move from a fragment to another one
  direction : to declare where exactly to go
  viewClicked : onWhich view the move will begin
 */
fun navigationDirectionAction(direction: NavDirections, viewClicked: View) {
    Navigation.findNavController(viewClicked).navigate(direction)
}

fun gridRecyclerView(
    context: Context?,
    entries: List<Item>,
    orientation: Int = RecyclerView.VERTICAL,
    itemsPerRow: Int = 2,
    view: RecyclerView?
): GroupAdapter<ViewHolder> {
    val adapter = GroupAdapter<ViewHolder>().apply {
        addAll(entries)
    }
    view?.apply {
        when (orientation) {
            RecyclerView.HORIZONTAL -> this.layoutManager =
                GridLayoutManager(context, itemsPerRow, RecyclerView.HORIZONTAL, false)

        }
        this.adapter = adapter
    }
    return adapter
}

fun generateRandomizedNumber(): Int {
    return (0..19).random()
}

fun autoSlideViewPager(
    viewPager: ViewPager?,
    pagerIndicator: PageIndicatorView?,
    items: Int,
    duration: Long = 3000
) {
    var isHandleStarted = false
    var currentPage = 0
    if (viewPager != null) {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                currentPage = position
                pagerIndicator?.setSelected(position)
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
        //isHandleStarted = true

    }

}

fun animateBottomTabsOnClick() {
    val transitionSet = TransitionSet()
    transitionSet.apply {
        addTransition(Fade())

    }

    fun <T> List<T>.toAdapter(): List<BaseAdapter<T>> {
        return this.map { item ->
            BaseAdapter(item)
        }
    }
}
