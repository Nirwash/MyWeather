package com.nirwashh.android.myweather.presenters

import com.nirwashh.android.myweather.view.MainView

class MainPresenter: BasePresenter<MainView>() {
    override fun enable() {

    }

    fun refresh(lat: String, lon: String) {
        viewState.setLoading(true)

    }
}