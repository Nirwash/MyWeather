package com.nirwashh.android.myweather.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nirwashh.android.myweather.R
import com.nirwashh.android.myweather.business.model.DailyWeatherModel
import com.nirwashh.android.myweather.databinding.ItemDailyMainBinding
import com.nirwashh.android.myweather.view.*
import java.lang.StringBuilder

class DailyListMainAdapter : BaseAdapter<DailyWeatherModel>() {

    lateinit var binding: ItemDailyMainBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        binding = ItemDailyMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyViewHolder(binding)
    }

    inner class DailyViewHolder(binding: ItemDailyMainBinding) : BaseViewHolder(binding.root) {

        override fun bindView(position: Int) {
            mData[position].apply {
                binding.apply {
                    itemTvDailyDate.text = dt.toDateFormatOf(DAY_WEEK_NAME_LONG)
                    itemTvDailyPop.text = pop.toPercentString("%")
                    itemTvDailyMinTemp.text = StringBuilder().append(temp.min.toDegree()).append("\u00b0").toString()
                    itemTvDailyMaxTemp.text = StringBuilder().append(temp.max.toDegree()).append("\u00b0").toString()
                    itemIcDailyWeatherCondition.setImageResource(weather[0].icon.provideIcon())
                }
            }


        }
    }
}