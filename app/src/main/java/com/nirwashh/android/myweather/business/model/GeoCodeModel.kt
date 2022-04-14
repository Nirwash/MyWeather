package com.nirwashh.android.myweather.business.model

data class GeoCodeModel(
    val country: String,
    val local_names: LocalNames,
    val lat: Double,
    val lon: Double,
    val name: String,
    val state: String?,
    var isFavorite: Boolean = false
)