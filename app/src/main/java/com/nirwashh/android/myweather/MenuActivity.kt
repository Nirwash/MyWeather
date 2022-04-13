package com.nirwashh.android.myweather

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.view.menu.MenuPresenter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nirwashh.android.myweather.business.model.GeoCodeModel
import com.nirwashh.android.myweather.databinding.ActivityMenuBinding
import com.nirwashh.android.myweather.view.adapters.CityListAdapter
import com.nirwashh.android.myweather.view.createObservable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_menu.*
import moxy.MvpAppCompatActivity
import java.util.concurrent.TimeUnit
import moxy.ktx.moxyPresenter


class MenuActivity : MvpAppCompatActivity(), com.nirwashh.android.myweather.view.MenuView {
    lateinit var binding: ActivityMenuBinding

    private val presenter by moxyPresenter { com.nirwashh.android.myweather.presenters.MenuPresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.enable()
        presenter.getFavoriteLocation()

        initCitiList(predictions)
        initCitiList(favorites)

        search_field.createObservable()
            .doOnNext { setLoading(true) }
            .debounce(700, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (!it.isNullOrEmpty()) presenter.searchFor(it)
            }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_left)
    }

    private fun initCitiList(rv: RecyclerView) {
        val cityAdapter = CityListAdapter()
        cityAdapter.clickListener = searchItemClickListener
        rv.apply {
            adapter = cityAdapter
            layoutManager = object : LinearLayoutManager(this@MenuActivity, VERTICAL, false) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            setHasFixedSize(true)

        }

    }

    private val searchItemClickListener = object : CityListAdapter.SearchItemClickListener {
        override fun removeFromFavorite(item: GeoCodeModel) {
            presenter.removeLocation(item)
        }

        override fun addToFavorite(item: GeoCodeModel) {
            presenter.saveLocation(item)
        }

        override fun showWeatherIn(item: GeoCodeModel) {
            val intent = Intent(this@MenuActivity, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putString("lat", item.lat.toString())
            bundle.putString("lon", item.lon.toString())
            intent.putExtra(COORDINATES, bundle)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_left)

        }
    }

    override fun setLoading(flag: Boolean) {
        loading.isActivated = true
        loading.visibility = if (flag) View.VISIBLE else View.GONE

    }

    override fun fillPredictionList(data: List<GeoCodeModel>) {
        (predictions.adapter as CityListAdapter).updateData(data)
    }

    override fun fillFavoriteList(data: List<GeoCodeModel>) {
        (favorites.adapter as CityListAdapter).updateData(data)
    }
}