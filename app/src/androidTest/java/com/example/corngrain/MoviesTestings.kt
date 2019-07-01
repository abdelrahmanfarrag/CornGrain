package com.example.corngrain

import android.util.Log
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.corngrain.data.network.response.movies.PlayingMovies
import com.example.corngrain.ui.main.MainActivity
import com.example.corngrain.ui.main.movies.adapters.PlayingAdapter
import com.xwray.groupie.ViewHolder
import org.hamcrest.CoreMatchers.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MoviesTestings {

    @Test
    fun runMoviesTest() {
//        ActivityScenario.launch(MainActivity::class.java)
        val rule = ActivityScenario.launch(MainActivity::class.java)
        // onData(anything()).onChildView(withId(R.id.bottom_navigation)).atPosition(1)
        //   .perform(click())

        onView(withId(R.id.movies_tab))
            .perform(click())
     //   onView(withId(R.id.now_playing_movies_list))
       //     .perform(RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(0, click()))

      //  onView(withId(R.id.detail_screen_title))
        //    .check(matches(withText("Toy Story 4")))

/*
    onData(instanceOf(PlayingMovies::class.java))
           .inAdapterView(allOf(withId(R.id.now_playing_movies_list)))
           .atPosition(0)
        .check(matches(withText("Hello")))*/

//            .check(matches(isDisplayed()))
//            .perform(click())
        // .perform(RecyclerViewActions())
//        onView(withRecyclerView())

        //          .check(matches(isDisplayed()))

        //  onData(instanceOf(PlayingAdapter::class.java))
        //    .atPosition(0)
        //   .perform(click())

        //onView(withId(R.id.detail_screen_title))
        //    .check(matches(withText("Hello")))
    }

}