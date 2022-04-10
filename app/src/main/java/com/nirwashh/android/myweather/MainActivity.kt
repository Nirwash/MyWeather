package com.nirwashh.android.myweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nirwashh.android.myweather.databinding.ActivityMainBinding
import com.nirwashh.android.myweather.view.adapters.DailyListMainAdapter
import com.nirwashh.android.myweather.view.adapters.HourlyListMainAdapter

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
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
}