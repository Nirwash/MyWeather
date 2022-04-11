package com.nirwashh.android.myweather.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nirwashh.android.myweather.R
import com.nirwashh.android.myweather.business.model.DailyWeatherModel
import com.nirwashh.android.myweather.databinding.ItemDailyMainBinding

class DailyListMainAdapter : BaseAdapter<DailyWeatherModel>() {

    lateinit var binding: ItemDailyMainBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        binding = ItemDailyMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyViewHolder(binding)
    }

    inner class DailyViewHolder(binding: ItemDailyMainBinding) : BaseViewHolder(binding.root) {

        override fun bindView(position: Int) {
            binding.apply {
                itemTvDailyDate.text = "25 Saturday"
                itemTvDailyPop.text = "15%"
                itemTvDailyMinTemp.text = "11\u00b0"
                itemTvDailyMaxTemp.text = "25\u00b0"
                itemIcDailyWeatherCondition.setImageResource(R.drawable.ic_sun)
            }

        }
    }
}