package com.nirwashh.android.myweather.presenters

import android.util.Log
import com.nirwashh.android.myweather.business.ApiProvider
import com.nirwashh.android.myweather.business.repos.MainRepository
import com.nirwashh.android.myweather.view.MainView

class MainPresenter: BasePresenter<MainView>() {

    private val repo = MainRepository(ApiProvider())
    override fun enable() {

        repo.dataEmitter.subscribe{response ->
            Log.d("MAINREPO", "Presenter enable(): $response ")
            viewState.displayLocation(response.cityName)
            viewState.displayCurrentData(response.weatherData)
            viewState.displayDailyData(response.weatherData.daily)
            viewState.displayHourlyData(response.weatherData.hourly)
            response.error?.let{viewState.displayError(response.error)}

        }
    }

    fun refresh(lat: String, lon: String) {
        viewState.setLoading(true)
        repo.repoadData(lat, lon)

    }
}