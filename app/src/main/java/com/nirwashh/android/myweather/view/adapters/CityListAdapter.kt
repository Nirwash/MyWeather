package com.nirwashh.android.myweather.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.nirwashh.android.myweather.R
import com.nirwashh.android.myweather.business.model.GeoCodeModel
import com.nirwashh.android.myweather.databinding.ItemCityListsBinding
import java.util.*

class CityListAdapter : BaseAdapter<GeoCodeModel>() {

    lateinit var clickListener: SearchItemClickListener
    lateinit var b: ItemCityListsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySearchViewHolder {
        b = ItemCityListsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CitySearchViewHolder(b)
    }

    interface SearchItemClickListener {
        fun addToFavorite(item: GeoCodeModel)
        fun removeFromFavorite(item: GeoCodeModel)
        fun showWeatherIn(item: GeoCodeModel)
    }

    inner class CitySearchViewHolder(binding: ItemCityListsBinding) : BaseViewHolder(binding.root) {
        override fun bindView(position: Int) {
                b.location.setOnClickListener {
                    clickListener.showWeatherIn(mData[position])
                }

                b.favorite.setOnClickListener {
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
                b.state.text = if (!state.isNullOrEmpty()) itemView.context.getString(
                    R.string.comma,
                    state
                ) else ""
                b.searchCity.text = when (Locale.getDefault().displayLanguage) {
                    "русский" -> local_names.ru ?: name
                    "english" -> local_names.en ?: name
                    else -> name
                }
                b.searchCountry.text = Locale("", country).displayName
                b.favorite.isChecked = isFavorite

            }

        }

    }
}


