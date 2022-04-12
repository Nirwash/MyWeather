package com.nirwashh.android.myweather.business

import com.nirwashh.android.myweather.business.model.GeoCodeModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodingApi {

    @GET("geo/1.0/reverse?")
    fun getCityByCoord(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("limit") limit: String = "10",
        @Query("appid") appid: String = "00cdc0ee3924708d349131ddb0350450"
    ) : Observable<List<GeoCodeModel>>
}