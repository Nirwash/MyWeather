package com.nirwashh.android.myweather

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nirwashh.android.myweather.databinding.ActivityInitialBinding

class InitialActivity : AppCompatActivity() {
    lateinit var binding: ActivityInitialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}