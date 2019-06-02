package com.example.corngrain.utilities

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*

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