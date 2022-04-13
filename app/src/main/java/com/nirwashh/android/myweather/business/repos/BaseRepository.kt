package com.nirwashh.android.myweather.business.repos

import com.nirwashh.android.myweather.App
import com.nirwashh.android.myweather.business.ApiProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

abstract class BaseRepository<T>(val api: ApiProvider) {

    val dataEmitter: BehaviorSubject<T> = BehaviorSubject.create()
    protected val db by lazy { App.db }

    protected fun roomTransaction(
        transaction: () -> T
    ) = Observable.fromCallable { transaction() }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { dataEmitter.onNext(it)}
}