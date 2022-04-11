package com.nirwashh.android.myweather

import android.annotation.SuppressLint

import android.location.Location

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.nirwashh.android.myweather.business.model.DailyWeatherModel
import com.nirwashh.android.myweather.business.model.HourlyWeatherModel
import com.nirwashh.android.myweather.business.model.WeatherDataModel
import com.nirwashh.android.myweather.databinding.ActivityMainBinding
import com.nirwashh.android.myweather.presenters.MainPresenter
import com.nirwashh.android.myweather.view.MainView
import com.nirwashh.android.myweather.view.adapters.DailyListMainAdapter
import com.nirwashh.android.myweather.view.adapters.HourlyListMainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter

const val TAG = "GEO_TEST"

class MainActivity : MvpAppCompatActivity(), MainView {
    lateinit var binding: ActivityMainBinding

    private val mainPresenter by moxyPresenter { MainPresenter() }

    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation: Location

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

        binding.hourlyListMain.apply {
            adapter = HourlyListMainAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        binding.dailyListMain.apply {
            adapter = DailyListMainAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        mainPresenter.enable()

        geoService.requestLocationUpdates(locationRequest, geoCallback, mainLooper)
    }

    private fun initViews() {
        binding.apply {
            tvCityMainAct.text = "Rostov-od-Don"
            tvDateMainAct.text = "31 Oktober"
            icWeatherConditionMain.setImageResource(R.drawable.ic_sun)
            tvWeatherConditionDescriptionMain.text = "Clear sky"
            tvTempMainAct.text = "25\u00B0"
            tvMinValueMainAct.text = "19"
            tvMaxValueMainAct.text = "28"
            tvAvgValueMainAct.text = "21"
            imgWeatherMainAct.setImageResource(R.mipmap.cloud3x)
            tvPressureMuMain.text = "1,5 hPa"
            tvHumidityMuMain.text = "55%"
            tvWindSpeedMuMain.text = "4 m/s"
            tvSunriseTimeMain.text = "6:45"
            tvSunsetTimeMain.text = "17:45"
        }
    }

    //---moxy-code---
    override fun displayLocation(data: String) {
        binding.tvCityMainAct.text = data
    }

    override fun displayCurrentData(data: WeatherDataModel) {
        binding.apply {
            tvCityMainAct.text = "Rostov-od-Don"
            tvDateMainAct.text = "31 Oktober"
            icWeatherConditionMain.setImageResource(R.drawable.ic_sun)
            tvWeatherConditionDescriptionMain.text = "Clear sky"
            tvTempMainAct.text = "25\u00B0"
            tvMinValueMainAct.text = "19"
            tvMaxValueMainAct.text = "28"
            tvAvgValueMainAct.text = "21"
            imgWeatherMainAct.setImageResource(R.mipmap.cloud3x)
            tvPressureMuMain.text = "1,5 hPa"
            tvHumidityMuMain.text = "55%"
            tvWindSpeedMuMain.text = "4 m/s"
            tvSunriseTimeMain.text = "6:45"
            tvSunsetTimeMain.text = "17:45"
        }
    }

    override fun displayHourlyData(data: List<HourlyWeatherModel>) {
        (hourly_list_main.adapter as HourlyListMainAdapter).updateData(data)
    }

    override fun displayDailyData(data: List<DailyWeatherModel>) {
        (daily_list_main.adapter as DailyListMainAdapter).updateData(data)
    }

    override fun displayError(error: Throwable) {

    }

    override fun setLoading(flag: Boolean) {

    }
    //---moxy-code---

    //-------------location code--------------

    private fun initLocationRequest(): com.google.android.gms.location.LocationRequest {
        val request = com.google.android.gms.location.LocationRequest.create()
        return request.apply {
            interval = 10000
            fastestInterval = 5000
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY

        }
    }

    private val geoCallback = object : LocationCallback() {
        override fun onLocationResult(geo: LocationResult) {
            Log.d(TAG, "onLocationResult: ${geo.locations.size}")
            for (location in geo.locations) {
                mLocation = location
                mainPresenter.refresh(mLocation.latitude.toString(), mLocation.longitude.toString())
                Log.d(
                    TAG,
                    "onLocationResult: lat: ${location.latitude} ; lon: ${location.longitude}"
                )
            }
        }
    }
}