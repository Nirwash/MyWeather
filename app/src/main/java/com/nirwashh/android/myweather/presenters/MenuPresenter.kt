package com.nirwashh.android.myweather.presenters

import android.util.Log
import com.nirwashh.android.myweather.business.ApiProvider
import com.nirwashh.android.myweather.business.model.GeoCodeModel
import com.nirwashh.android.myweather.business.repos.MenuRepository
import com.nirwashh.android.myweather.business.repos.SAVED
import com.nirwashh.android.myweather.view.MenuView

class MenuPresenter : BasePresenter<MenuView>() {
    private val repo = MenuRepository(ApiProvider())
    override fun enable() {
        repo.dataEmitter.subscribe {
            viewState.setLoading(false)
            if (it.purpose == SAVED) {
                Log.d("Tag", "enable: SAVED ${it.data}")
                viewState.fillFavoriteList(it.data)
            } else {
                Log.d("Tag", "enable: CURRENT ${it.data}")
            }
        }
    }

    fun searchFor(string: String) {
        repo.getCities(string)
    }

    fun removeLocation(data: GeoCodeModel) {
        repo.remove(data)
    }

    fun saveLocation(data: GeoCodeModel) {
        repo.add(data)
    }

    fun getFavoriteLocation() {
        repo.updateFavorite()
    }
}