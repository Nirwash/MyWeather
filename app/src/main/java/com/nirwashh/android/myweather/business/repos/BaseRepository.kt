package com.nirwashh.android.myweather.business.repos

import com.nirwashh.android.myweather.business.ApiProvider
import io.reactivex.rxjava3.subjects.BehaviorSubject

abstract class BaseRepository<T>(val api: ApiProvider) {

    val dataEmitter: BehaviorSubject<T> = BehaviorSubject.create()
}