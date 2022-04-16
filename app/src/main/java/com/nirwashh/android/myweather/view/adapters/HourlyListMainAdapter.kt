package com.nirwashh.android.myweather.view.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textview.MaterialTextView
import com.nirwashh.android.myweather.R
import com.nirwashh.android.myweather.business.model.HourlyWeatherModel
import com.nirwashh.android.myweather.databinding.ItemHourlyMainBinding
import com.nirwashh.android.myweather.view.*
import java.lang.StringBuilder

class HourlyListMainAdapter :
    com.nirwashh.android.myweather.view.adapters.BaseAdapter<HourlyWeatherModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hourly_main, parent, false)
        return HourlyViewHolder(view)
    }

    inner class HourlyViewHolder(view: View) : BaseViewHolder(view) {

        @BindView(R.id.item_tv_hourly_time)
        lateinit var time: MaterialTextView
        @BindView(R.id.item_tv_hourly_temp)
        lateinit var temperature: MaterialTextView
        @BindView(R.id.item_tv_hourly_pop)
        lateinit var popRate: MaterialTextView
        @BindView(R.id.item_ic_hourly_weather_condition)
        lateinit var icon: ImageView
        init {
            ButterKnife.bind(this, itemView)
        }

        override fun bindView(position: Int) {
            mData[position].apply {
                    time.text = dt.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
                        temperature.text = StringBuilder().append(temp.toDegree()).append("\u00b0").toString()
                    popRate.text = pop.toPercentString("%")
                    icon.setImageResource(weather[0].icon.provideIcon())}

            }


    }

}