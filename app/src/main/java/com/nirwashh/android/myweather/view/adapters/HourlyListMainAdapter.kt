package com.nirwashh.android.myweather.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nirwashh.android.myweather.R
import com.nirwashh.android.myweather.business.model.HourlyWeatherModel
import com.nirwashh.android.myweather.databinding.ItemHourlyMainBinding
import com.nirwashh.android.myweather.view.*
import java.lang.StringBuilder

class HourlyListMainAdapter :
    com.nirwashh.android.myweather.view.adapters.BaseAdapter<HourlyWeatherModel>() {

    lateinit var binding: ItemHourlyMainBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        binding = ItemHourlyMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyViewHolder(binding)
    }

    inner class HourlyViewHolder(binding: ItemHourlyMainBinding) : BaseViewHolder(binding.root) {

        override fun bindView(position: Int) {
            mData[position].apply {
                binding.apply {
                    itemTvHourlyTime.text = dt.toDateFormatOf(HOUR_DOUBLE_DOT_MINUTE)
                    itemTvHourlyTemp.text = StringBuilder().append(temp.toDegree()).append("\u00b0").toString()
                    itemTvHourlyPop.text = pop.toPercentString("%")
                    itemIcHourlyWeatherCondition.setImageResource(weather[0].icon.provideIcon())}
            }
            }


    }

}