package com.nirwashh.android.myweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButtonToggleGroup
import com.nirwashh.android.myweather.databinding.ActivitySettingsBinding
import com.nirwashh.android.myweather.view.SettingsHolder

class SettingsActivity : AppCompatActivity() {
    lateinit var b: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.innerToolbar.setNavigationOnClickListener { onBackPressed() }

        setSavedSettings()

        listOf(b.groupTemperature, b.groupWindSpeed, b.groupPressure).forEach {
            it.addOnButtonCheckedListener(
                ToggleButtonCheckedListener
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SettingsHolder.onDestroy()
    }

    private fun setSavedSettings() {
        b.apply {
            groupTemperature.check(SettingsHolder.temp.checkedViewId)
            groupWindSpeed.check(SettingsHolder.windSpeed.checkedViewId)
            groupPressure.check(SettingsHolder.pressure.checkedViewId)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_right)
    }

    private object ToggleButtonCheckedListener : MaterialButtonToggleGroup.OnButtonCheckedListener {
        override fun onButtonChecked(
            group: MaterialButtonToggleGroup?,
            checkedId: Int,
            isChecked: Boolean
        ) {
            when (checkedId to isChecked) {
                R.id.btn_degreeC to true -> SettingsHolder.temp =
                    SettingsHolder.Setting.TEMP_CELSIUS
                R.id.btn_degreeF to true -> SettingsHolder.temp =
                    SettingsHolder.Setting.TEMP_FAHRENHEIT
                R.id.btn_windSpeed_m_s to true -> SettingsHolder.windSpeed =
                    SettingsHolder.Setting.WIND_SPEED_MS
                R.id.btn_windSpeed_km_h to true -> SettingsHolder.windSpeed =
                    SettingsHolder.Setting.WIND_SPEED_KMH
                R.id.btn_pressure_mmHg to true -> SettingsHolder.pressure =
                    SettingsHolder.Setting.PRESSURE_MMHG
                R.id.btn_pressure_hPa to true -> SettingsHolder.pressure =
                    SettingsHolder.Setting.PRESSURE_HPA
            }
        }
    }
}