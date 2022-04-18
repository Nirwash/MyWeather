package com.nirwashh.android.myweather

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentSender
import android.graphics.Point

import android.location.Location
import android.location.LocationRequest

import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.tasks.CancellationTokenSource
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
import java.lang.Exception
import java.lang.StringBuilder
import kotlin.math.roundToInt

const val TAG = "GEO_TEST"
const val COORDINATES = "Coordinates"

class MainActivity : MvpAppCompatActivity(), MainView {
    lateinit var b: ActivityMainBinding

    private val mainPresenter by moxyPresenter { MainPresenter() }
    private val tokenSource: CancellationTokenSource = CancellationTokenSource()
    private val geoService by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy { initLocationRequest() }
    private val geoCallback = object : LocationCallback() {
        override fun onLocationResult(geo: LocationResult) {
            Log.d(TAG, "onLocationResult: callback ${geo.locations[0]}")
            mLocation = geo.locations[0]
            mainPresenter.refresh(mLocation.latitude.toString(), mLocation.longitude.toString())
        }
    }
    private lateinit var mLocation: Location

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        initBottomSheets()
        initViews()
        initSwipeRefresh()

        refresh.isRefreshing = true

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, DailyListFragment(), DailyListFragment::class.simpleName)
            .commit()

        if (!intent.hasExtra(COORDINATES)) {
            checkGeoAvailability()
            Log.d(TAG, "onCreate: ")
            getGeo()
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
                b.tvPressureMuMain.text = getString(
                    pressureSet.measureUnitStringRes,
                    pressureSet.getValue(current.pressure.toDouble())
                )

                val windSpeedSet = SettingsHolder.windSpeed
                b.tvWindSpeedMuMain.text = getString(
                    pressureSet.measureUnitStringRes,
                    pressureSet.getValue(current.wind_speed)
                )

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
        (supportFragmentManager.findFragmentByTag(DailyListFragment::class.simpleName) as DailyListFragment).setData(
            data
        )
    }

    override fun displayError(error: Throwable) {

    }

    override fun setLoading(flag: Boolean) {
        refresh.isRefreshing = flag

    }
    //---moxy-code---

    //-------------location code--------------

    private fun initLocationRequest(): com.google.android.gms.location.LocationRequest {
        val request = com.google.android.gms.location.LocationRequest.create()
        return request.apply {
            interval = 1000000
            fastestInterval = 5000
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY

        }
    }



    private fun initBottomSheets() {
        bottom_sheets_main.isNestedScrollingEnabled = true
        val size = Point()
        windowManager.defaultDisplay.getSize(size)
        bottom_sheets_container_main.layoutParams =
            CoordinatorLayout.LayoutParams(size.x, (size.y * 0.55).roundToInt())
    }

    private fun initSwipeRefresh() {
        refresh.apply {
            setColorSchemeResources(R.color.purple_700)
            setProgressViewEndTarget(false, 280)
            setOnRefreshListener {
                mainPresenter.refresh(mLocation.latitude.toString(), mLocation.longitude.toString())

            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getGeo() {
        geoService
            .getCurrentLocation(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY, tokenSource.token)
            .addOnSuccessListener {
                Log.d(TAG, "getGeo: ")
                if (it != null) {
                    mLocation = it
                    mainPresenter.refresh(mLocation.latitude.toString(), mLocation.longitude.toString())
                } else {
                    displayError(Exception("Geodata is not available"))
                }
                Log.d(TAG, "requestGeo: $it")
            }
    }

    private fun checkGeoAvailability() {
        Log.d(TAG, "checkGeoAvailability: ")
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(this, 100)
                } catch (sendEx: IntentSender.SendIntentException) {

                }
            }
        }
    }


}