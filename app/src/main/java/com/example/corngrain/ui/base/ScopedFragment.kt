package com.example.corngrain.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


/* ScopedFragment is used to create a local coroutine scope
 * this is useful to stop listening to data after fragment is destroyed so , we can not get a crash
 * from coroutine :D*
 */
abstract class ScopedFragment : Fragment(), CoroutineScope {
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Main



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
       retainInstance = true// <--------- the fragment retain his configuration

    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}