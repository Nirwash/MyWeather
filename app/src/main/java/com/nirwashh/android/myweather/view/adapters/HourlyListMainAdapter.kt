package com.nirwashh.android.myweather.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nirwashh.android.myweather.R
import com.nirwashh.android.myweather.business.model.HourlyWeatherModel
import com.nirwashh.android.myweather.databinding.ItemHourlyMainBinding

class HourlyListMainAdapter :
    com.nirwashh.android.myweather.view.adapters.BaseAdapter<HourlyWeatherModel>() {

    lateinit var binding: ItemHourlyMainBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        binding = ItemHourlyMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyViewHolder(binding)
    }

    inner class HourlyViewHolder(binding: ItemHourlyMainBinding) : BaseViewHolder(binding.root) {

        override fun bindView(position: Int) {
            binding.apply {
                itemTvHourlyTime.text = "13:00"
                itemTvHourlyTemp.text = "14\u00b0"
                itemTvHourlyPop.text = "25%"
                itemIcHourlyWeatherCondition.setImageResource(R.drawable.ic_sun)}
        }

    }

}