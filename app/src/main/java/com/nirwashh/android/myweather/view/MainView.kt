package com.nirwashh.android.myweather.view

import com.nirwashh.android.myweather.business.model.DailyWeatherModel
import com.nirwashh.android.myweather.business.model.HourlyWeatherModel
import com.nirwashh.android.myweather.business.model.WeatherDataModel
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface MainView : MvpView {

    @AddToEndSingle
    fun displayLocation(data: String)

    @AddToEndSingle
    fun displayCurrentData(data: WeatherDataModel)

    @AddToEndSingle
    fun displayHourlyData(data: List<HourlyWeatherModel>)

    @AddToEndSingle
    fun displayDailyData(data: List<DailyWeatherModel>)

    @AddToEndSingle
    fun displayError(error: Throwable)

    @AddToEndSingle
    fun setLoading(flag: Boolean)
}