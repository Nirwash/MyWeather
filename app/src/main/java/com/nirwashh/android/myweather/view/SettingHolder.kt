package com.nirwashh.android.myweather.view

import android.content.SharedPreferences
import android.provider.Contacts.Settings.getSetting
import androidx.annotation.IdRes
import com.nirwashh.android.myweather.R
import java.util.*
import kotlin.math.roundToInt

const val TEMP = "Temp"
const val WIND_SPEED = "Wind speed"
const val PRESSURE = "Pressure"

object SettingsHolder {

    private lateinit var preferences: SharedPreferences
    var temp = Setting.TEMP_CELSIUS
    var windSpeed = Setting.WIND_SPEED_MS
    var pressure = Setting.PRESSURE_MMHG

    fun onCreate(pref: SharedPreferences) {
        preferences = pref
        temp = getSetting(preferences.getInt(TEMP, C))
        windSpeed = getSetting(preferences.getInt(WIND_SPEED, MS))
        pressure = getSetting(preferences.getInt(PRESSURE, MM_HG))
    }

    fun onDestroy() {
        val editor = preferences.edit()
        editor.putInt(TEMP, temp.prefConst)
        editor.putInt(WIND_SPEED, windSpeed.prefConst)
        editor.putInt(PRESSURE, pressure.prefConst)
        editor.apply()
    }

    private fun getSetting(@IdRes id: Int) = when(id) {
        C -> Setting.TEMP_CELSIUS
        F -> Setting.TEMP_FAHRENHEIT
        MS -> Setting.WIND_SPEED_MS
        KMH -> Setting.WIND_SPEED_KMH
        MM_HG -> Setting.PRESSURE_MMHG
        HPA -> Setting.PRESSURE_HPA
        else -> throw InputMismatchException()
    }

    const val C = 1
    const val F = 2
    const val MS = 3
    const val KMH = 4
    const val MM_HG = 5
    const val HPA = 6

    enum class Setting(@IdRes val checkedViewId: Int, @IdRes val measureUnitStringRes: Int, val prefConst: Int ) {
        TEMP_CELSIUS(R.id.btn_degreeC, R.string.c, C) {
            override fun getValue(initValue: Double) = valueToString { initValue - 273.15 }
        },
        TEMP_FAHRENHEIT(R.id.btn_degreeF, R.string.f, F) {
            override fun getValue(initValue: Double) = valueToString { (initValue - 273.15) * (9/5) + 32 }
        },
        WIND_SPEED_MS(R.id.btn_windSpeed_m_s, R.string.wind_speed_mu_ms, MS) {
            override fun getValue(initValue: Double) = valueToString { initValue }
        },
        WIND_SPEED_KMH(R.id.btn_windSpeed_km_h, R.string.wind_speed_mu_kmh, KMH) {
            override fun getValue(initValue: Double) = valueToString { initValue * 3.6 }
        },
        PRESSURE_MMHG(R.id.btn_pressure_mmHg, R.string.pressure_mu_mmHg, MM_HG) {
            override fun getValue(initValue: Double) = valueToString { initValue / 1.33322387415}
        },
        PRESSURE_HPA(R.id.btn_pressure_hPa, R.string.pressure_mu_hpa, HPA) {
            override fun getValue(initValue: Double) = valueToString { initValue }
        };
        abstract fun getValue(initValue: Double): String
        protected fun valueToString(formula: () -> Double) = formula().roundToInt().toString()
    }


}