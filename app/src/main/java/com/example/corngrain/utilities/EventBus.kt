package com.example.corngrain.utilities

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class EventBus {

    companion object {
        val publishSubject: PublishSubject<Any> = PublishSubject.create()
        inline fun <reified T> subscribe(): Observable<T> {
            return publishSubject.filter {
                it is T
            }.map {
                it as T
            }
        }

        fun post(event: Any) {
            publishSubject.onNext(event)
        }
    }
}