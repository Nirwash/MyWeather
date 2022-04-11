package com.nirwashh.android.myweather.business

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/onecall?")
    fun getWeatherForecast(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("exclude") exclude: String = "minutely,alerts",
        @Query("appid") appid: String = "00cdc0ee3924708d349131ddb0350450",
        @Query("lang") lang: String = "en"
    ) // TODO указать возвращаемый тип, как обсервер
}