package com.nirwashh.android.myweather.business.repos

import android.util.Log
import com.nirwashh.android.myweather.business.ApiProvider
import com.nirwashh.android.myweather.business.model.WeatherDataModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

const val TAG = "MAINREPO"
class MainRepository(api: ApiProvider) : BaseRepository<MainRepository.ServerPesponce>(api) {

    fun repoadData(lat: String, lon: String) {
        Observable.zip(
            api.provideWeatherApi().getWeatherForecast(lat, lon),
            api.provideGeoCodeApi().getCityByCoord(lat, lon).map {
                it.asSequence()
                    .map { model -> model.name }
                    .toList()
                    .filterNotNull()
                    .first()
            },
            { weatherData, geoCode -> ServerPesponce(geoCode, weatherData) }
        )
            .subscribeOn(Schedulers.io())
            .doOnNext{ /* TODO тут будет добавление объекта в БД */}
            /*.onErrorResumeNext { TODO тут будет извлечение объекта из БД }*/
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                       dataEmitter.onNext(it)
            },{
                Log.d(TAG, "reloadData: $it")
            })

    }

    data class ServerPesponce(
        val cityName: String,
        val weatherData: WeatherDataModel,
        val error: Throwable? = null
    ) {}
}