package com.nirwashh.android.myweather.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.nirwashh.android.myweather.R
import com.nirwashh.android.myweather.business.model.GeoCodeModel
import com.nirwashh.android.myweather.databinding.ActivityMenuBinding
import com.nirwashh.android.myweather.databinding.ItemCityListsBinding

class CityListAdapter : BaseAdapter<GeoCodeModel>() {

    lateinit var clickListener: SearchItemClickListener
    lateinit var binding: ItemCityListsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySearchViewHolder {
        binding = ItemCityListsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitySearchViewHolder(binding)
    }

    interface SearchItemClickListener {
        fun addToFavorite(item: GeoCodeModel)
        fun removeFromFavorite(item: GeoCodeModel)
        fun showWeatherIn(item: GeoCodeModel)
    }

    inner class CitySearchViewHolder(binding: ItemCityListsBinding) : BaseViewHolder(binding.root) {
        override fun bindView(position: Int) {
                binding.location.setOnClickListener {
                    clickListener.showWeatherIn(mData[position])
                }

                binding.btnFavorite.setOnClickListener {
                    val item = mData[position]
                    when ((it as MaterialButton).isChecked) {
                        true -> {
                            item.isFavorite = true
                            clickListener.addToFavorite(item)
                        }
                        false -> {
                            item.isFavorite = false
                            clickListener.removeFromFavorite(item)
                        }

                    }
                }



            mData[position].apply {
                binding.state.text = if (!state.isNullOrEmpty()) itemView.context.getString(
                    R.string.comma,
                    state
                ) else ""
                binding.searchCity.text = local_names.ru
                binding.searchCountry.text = country
                binding.btnFavorite.isChecked = isFavorite

            }

        }

    }
}


