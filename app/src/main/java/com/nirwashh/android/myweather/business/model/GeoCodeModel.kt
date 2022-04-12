package com.nirwashh.android.myweather.business.model

data class GeoCodeModel(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String?,
    var isFavorite: Boolean = false //TODO будет применяться при добавление городов в любимые в menuActivity
)