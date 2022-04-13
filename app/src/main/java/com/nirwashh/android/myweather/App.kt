package com.nirwashh.android.myweather

import android.app.Application
import android.content.Intent
import androidx.room.ProvidedAutoMigrationSpec
import androidx.room.Room
import com.nirwashh.android.myweather.business.room.OpenWeatherDatabase

const val APP_SETTINGS = "App settings"
const val IS_STARTED_UP = "Is started app"

class App : Application() {

    companion object {
        lateinit var db: OpenWeatherDatabase
    }

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(this, OpenWeatherDatabase::class.java, "OpenWeatherDB")
            .fallbackToDestructiveMigration().build()
        val preferences = getSharedPreferences(APP_SETTINGS, MODE_PRIVATE)

        val flag = preferences.contains(IS_STARTED_UP)

        if (!flag) {
            val editor = preferences.edit()
            editor.putBoolean(IS_STARTED_UP, true)
            editor.apply()
            val intent = Intent(this, InitialActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
