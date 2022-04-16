package com.nirwashh.android.myweather

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nirwashh.android.myweather.business.model.DailyWeatherModel
import com.nirwashh.android.myweather.view.*
import kotlinx.android.synthetic.main.fragment_day_info.*

class DailyInfoFragment : DailyBaseFragment<DailyWeatherModel>() {

    private lateinit var viewContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_day_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_back.setOnClickListener {
            fm.popBackStack()
        }

        updateView()
        viewContext = view.context
    }




    @SuppressLint("ResourceType")
    override fun updateView() {
        mData?.apply {
            day_date.text = dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
            day_temp.text = viewContext.getString(R.string.degree_symbol, temp.getAverage().toDegree())
            day_icon.setImageResource(weather[0].icon.provideIcon())
            day_morn_temp.text = viewContext.getString(R.string.degree_symbol, temp.morn.toDegree())
            day_morn_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.morn.toDegree())
            day_daily_temp.text = viewContext.getString(R.string.degree_symbol, temp.day.toDegree())
            day_daily_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.day.toDegree())
            day_eve_temp.text = viewContext.getString(R.string.degree_symbol, temp.eve.toDegree())
            day_eve_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.eve.toDegree())
            day_night_temp.text = viewContext.getString(R.string.degree_symbol, temp.night.toDegree())
            day_night_fl.text = viewContext.getString(R.string.degree_symbol, feels_like.night.toDegree())
            tv_humidity_fragment.text = ("$humidity %")
            val settingPressure = SettingsHolder.pressure
            tv_pressure_fragment.text = viewContext.getString(settingPressure.measureUnitStringRes, settingPressure.getValue(pressure.toDouble()))
            val settingWindSpeed = SettingsHolder.windSpeed
            tv_wind_speed_fragment.text = viewContext.getString(settingWindSpeed.measureUnitStringRes, settingPressure.getValue(wind_speed))
            tv_wind_dir_fragment.text = wind_deg.toString()
            tv_sunrise_time_fragment.text = sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
            tv_sunset_time_fragment.text = sunset.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)

        }
    }
}