package com.nirwashh.android.myweather.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.nirwashh.android.myweather.R
import com.nirwashh.android.myweather.business.model.DailyWeatherModel
import com.nirwashh.android.myweather.view.*
import java.lang.StringBuilder

class DailyListMainAdapter : BaseAdapter<DailyWeatherModel>() {

    lateinit var clickListener: DayItemClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_main, parent, false)
        return DailyViewHolder(view)
    }

    interface DayItemClick {
        fun showDetails(data: DailyWeatherModel)
    }


    @SuppressLint("NonConstantResourceId")
    inner class DailyViewHolder(view: View) : BaseViewHolder(view) {


        @BindView(R.id.day_container)
        lateinit var conteiner: MaterialCardView

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
            val itemData = mData[position]
            val defaultTextColor = date.textColors
            if (position == 0) {
                date.setTextColor(ContextCompat.getColor(date.context, R.color.purple_500))
            } else {
                date.setTextColor(defaultTextColor)
            }



            conteiner.setOnClickListener {
                clickListener.showDetails(itemData)
            }
            if (mData.isNotEmpty()) {
                itemData.apply {
                    val dateOfDay = dt.toDateFormatOf(DAY_WEEK_NAME_LONG)
                    date.text = if (dateOfDay.startsWith(
                            "0",
                            true,
                        )
                    ) dateOfDay.removePrefix("0") else dateOfDay
                    if (pop < 0.01) {
                        popRate.visibility = View.INVISIBLE
                    } else {
                        popRate.visibility = View.VISIBLE
                        popRate.text = pop.toPercentString("%")
                    }
                    icon.setImageResource(weather[0].icon.provideIcon())
                    minTemperature.text =
                        StringBuilder().append(temp.min.toDegree()).append("\u00b0").toString()
                    maxTemperature.text =
                        StringBuilder().append(temp.max.toDegree()).append("\u00b0").toString()
                }


            }


        }
    }
}