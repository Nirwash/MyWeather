package com.nirwashh.android.myweather.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.nirwashh.android.myweather.R
import com.nirwashh.android.myweather.business.model.GeoCodeModel
import com.nirwashh.android.myweather.databinding.ItemCityListsBinding
import java.util.*

class CityListAdapter : BaseAdapter<GeoCodeModel>() {

    lateinit var clickListener: SearchItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitySearchViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_city_lists, parent, false)
        return CitySearchViewHolder(view)
    }

    interface SearchItemClickListener {
        fun addToFavorite(item: GeoCodeModel)
        fun removeFromFavorite(item: GeoCodeModel)
        fun showWeatherIn(item: GeoCodeModel)
    }

    @SuppressLint("NonConstantResourceId")
    inner class CitySearchViewHolder(view: View) : BaseViewHolder(view) {

        @BindView(R.id.search_city)
        lateinit var searchCity: MaterialTextView

        @BindView(R.id.search_country)
        lateinit var searchCountry: MaterialTextView

        @BindView(R.id.favorite)
        lateinit var favorite: MaterialButton

        @BindView(R.id.location)
        lateinit var location: LinearLayout

        @BindView(R.id.state)
        lateinit var mState: MaterialTextView

        init {
            ButterKnife.bind(this, itemView)
        }

        override fun bindView(position: Int) {
            location.setOnClickListener {
                clickListener.showWeatherIn(mData[position])
            }

            favorite.setOnClickListener {
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
                mState.text = if (!state.isNullOrEmpty()) itemView.context.getString(
                    R.string.comma,
                    state
                ) else ""
                searchCity.text = when (Locale.getDefault().displayLanguage) {
                    "русский" -> local_names.ru ?: name
                    "english" -> local_names.en ?: name
                    else -> name
                }
                searchCountry.text = Locale("", country).displayName
                favorite.isChecked = isFavorite

            }

        }

    }
}


