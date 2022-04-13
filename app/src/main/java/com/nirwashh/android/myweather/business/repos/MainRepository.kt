package com.nirwashh.android.myweather.business.repos

import android.util.Log
import com.google.gson.Gson
import com.nirwashh.android.myweather.business.ApiProvider
import com.nirwashh.android.myweather.business.model.WeatherDataModel
import com.nirwashh.android.myweather.business.room.WeatherDataEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

const val TAG = "MAINREPO"

class MainRepository(api: ApiProvider) : BaseRepository<MainRepository.ServerResponse>(api) {
    private val gson = Gson()
    private val dbAccess = db.getWeatherDao()

    fun repoadData(lat: String, lon: String) {
        Observable.zip(
            api.provideWeatherApi().getWeatherForecast(lat, lon),
            api.provideGeoCodeApi().getCityByCoord(lat, lon).map {
                it.asSequence()
                    .map { model -> model.name }
                    .toList()
                    // TODO настроить локализацию проекта
                    .filterNotNull()
                    .first()
            }
        ) { weatherData, geoCode -> ServerResponse(geoCode, weatherData) }
            .subscribeOn(Schedulers.io())
            .doOnNext {
                dbAccess.insertWeatherData(
                    WeatherDataEntity(
                        data = gson.toJson(it.weatherData),
                        city = it.cityName
                    )
                )
            }
            .onErrorResumeNext {
                Observable.just(
                    ServerResponse(
                        dbAccess.getWeatherData().city,
                        gson.fromJson(dbAccess.getWeatherData().data, WeatherDataModel::class.java),
                        it
                    )
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                dataEmitter.onNext(it)
            }, {
                Log.d(TAG, "reloadData: $it")
            })

    }

    data class ServerResponse(
        val cityName: String,
        val weatherData: WeatherDataModel,
        val error: Throwable? = null
    ) {}
}