package io.jpelczar.appforeverything.core.event

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject


class RxBus {

    private val bus = PublishSubject.create<Event>()

    fun post(eventData: Event) {
        bus.onNext(eventData)
    }

    fun register(): Observable<Event> {
        return bus
    }

    fun unregister(disposable: Disposable) {
        if (!disposable.isDisposed)
            disposable.dispose()
    }
}