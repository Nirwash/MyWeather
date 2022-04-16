package com.nirwashh.android.myweather

import android.annotation.SuppressLint
import android.content.Intent

import android.location.Location

import android.os.Bundle
import android.os.Looper
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
import com.nirwashh.android.myweather.view.*
import com.nirwashh.android.myweather.view.adapters.DailyListMainAdapter
import com.nirwashh.android.myweather.view.adapters.HourlyListMainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.lang.StringBuilder

const val TAG = "GEO_TEST"
const val COORDINATES = "Coordinates"

class MainActivity : MvpAppCompatActivity(), MainView {
    lateinit var b: ActivityMainBinding

    private val mainPresenter by moxyPresenter { MainPresenter() }

    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private lateinit var mLocation: Location

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        initViews()
        if (!intent.hasExtra(COORDINATES)) {
            geoService.requestLocationUpdates(locationRequest, geoCallback, Looper.getMainLooper())
        } else {
            val coord = intent.extras!!.getBundle(COORDINATES)!!
            val loc = Location("")
            loc.latitude = coord.getString("lat")!!.toDouble()
            loc.longitude = coord.getString("lon")!!.toDouble()
            mLocation = loc
            mainPresenter.refresh(
                lat = mLocation.latitude.toString(),
                lon = mLocation.longitude.toString()
            )
        }

        b.btnMenuMainAct.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, android.R.anim.fade_out)
        }

        b.btnSettingsMainAct.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out)
        }

        b.hourlyListMain.apply {
            adapter = HourlyListMainAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }
        b.dailyListMain.apply {
            adapter = DailyListMainAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }

        mainPresenter.enable()


    }

    private fun initViews() {
        b.apply {
            tvCityMainAct.text = "Earth"
            tvDateMainAct.text = "01 January"
            icWeatherConditionMain.setImageResource(R.drawable.ic_sun)
            tvWeatherConditionDescriptionMain.text = "Clear sky"
            tvTempMainAct.text = "00\u00B0"
            tvMinValueMainAct.text = "00"
            tvMaxValueMainAct.text = "00"
            tvAvgValueMainAct.text = "00"
            imgWeatherMainAct.setImageResource(R.drawable.image_sun_cloud)
            tvPressureMuMain.text = "00 hPa"
            tvHumidityMuMain.text = "00%"
            tvWindSpeedMuMain.text = "00 m/s"
            tvSunriseTimeMain.text = "00:00"
            tvSunsetTimeMain.text = "00:00"
        }
    }

    //---moxy-code---
    override fun displayLocation(data: String) {
        b.tvCityMainAct.text = data
    }

    @SuppressLint("ResourceType")
    override fun displayCurrentData(data: WeatherDataModel) {
        data.apply {
            b.apply {
                tvDateMainAct.text = current.dt.toDateFormatOf(DAY_FULL_MONTH_NAME)
                icWeatherConditionMain.setImageResource(current.weather[0].icon.provideIcon())
                tvWeatherConditionDescriptionMain.text = current.weather[0].description
                tvTempMainAct.text =
                    StringBuilder().append(current.temp.toDegree()).append("\u00b0").toString()
                daily[0].temp.apply {
                    tvMinValueMainAct.text =
                        StringBuilder().append(min.toDegree()).append("\u00b0").toString()
                    tvMaxValueMainAct.text =
                        StringBuilder().append(max.toDegree()).append("\u00b0").toString()
                    tvAvgValueMainAct.text =
                        StringBuilder().append(eve.toDegree()).append("\u00b0").toString()
                }

                val pressureSet = SettingsHolder.pressure
                b.tvPressureMuMain.text = getString(pressureSet.measureUnitStringRes, pressureSet.getValue(current.pressure.toDouble()))

                val windSpeedSet = SettingsHolder.windSpeed
                b.tvWindSpeedMuMain.text = getString(pressureSet.measureUnitStringRes, pressureSet.getValue(current.wind_speed))

                imgWeatherMainAct.setImageResource(current.weather[0].icon.provideImage())
                tvPressureMuMain.text =
                    StringBuilder().append(current.pressure.toString()).append(" hPa").toString()
                tvHumidityMuMain.text =
                    StringBuilder().append(current.humidity.toString()).append(" %").toString()
                tvWindSpeedMuMain.text =
                    StringBuilder().append(current.wind_speed.toString()).append(" m/s").toString()
                tvSunriseTimeMain.text = current.sunrise.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
                tvSunsetTimeMain.text = current.sunset.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
            }
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