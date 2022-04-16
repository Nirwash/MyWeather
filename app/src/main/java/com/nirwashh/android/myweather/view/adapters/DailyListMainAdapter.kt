package com.nirwashh.android.myweather.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.textview.MaterialTextView
import com.nirwashh.android.myweather.R
import com.nirwashh.android.myweather.business.model.DailyWeatherModel
import com.nirwashh.android.myweather.databinding.ItemDailyMainBinding
import com.nirwashh.android.myweather.view.*
import java.lang.StringBuilder

class DailyListMainAdapter : BaseAdapter<DailyWeatherModel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_main, parent, false)
        return DailyViewHolder(view)
    }

    interface DailyItemClick {
        fun showDetails(data: DailyWeatherModel)
    }


    @SuppressLint("NonConstantResourceId")
    inner class DailyViewHolder(view: View) : BaseViewHolder(view) {


        @BindView(R.id.item_tv_daily_date)
        lateinit var date: MaterialTextView

        @BindView(R.id.item_tv_daily_pop)
        lateinit var popRate: MaterialTextView

        @BindView(R.id.item_tv_daily_minTemp)
        lateinit var minTemperature: MaterialTextView

        @BindView(R.id.item_tv_daily_maxTemp)
        lateinit var maxTemperature: MaterialTextView

        @BindView(R.id.item_ic_daily_weather_condition)
        lateinit var icon: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }


        override fun bindView(position: Int) {
            mData[position].apply {
                date.text = dt.toDateFormatOf(DAY_WEEK_NAME_LONG)
                popRate.text = pop.toPercentString("%")
                minTemperature.text =
                    StringBuilder().append(temp.min.toDegree()).append("\u00b0").toString()
                maxTemperature.text =
                    StringBuilder().append(temp.max.toDegree()).append("\u00b0").toString()
                icon.setImageResource(weather[0].icon.provideIcon())

            }


        }
    }
}