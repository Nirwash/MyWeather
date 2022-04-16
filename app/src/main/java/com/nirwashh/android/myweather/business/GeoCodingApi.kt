package com.nirwashh.android.myweather.business

import com.nirwashh.android.myweather.business.model.GeoCodeModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodingApi {

    @GET("geo/1.0/direct?")
    fun getCityByName(
        @Query ("q") name: String,
        @Query ("limit") limit: String = "5",
        @Query ("appid") appid: String = "00cdc0ee3924708d349131ddb0350450"
    ) : Observable<List<GeoCodeModel>>

    @GET("geo/1.0/reverse?")
    fun getCityByCord(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("limit") limit: String = "5",
        @Query("appid") id: String = "00cdc0ee3924708d349131ddb0350450"
    ) : Observable<List<GeoCodeModel>>


}