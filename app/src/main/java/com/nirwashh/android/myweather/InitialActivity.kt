package com.nirwashh.android.myweather

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nirwashh.android.myweather.databinding.ActivityInitialBinding

const val GEO_LOCATION_REQUESTED_COD_SUCCESS = 1

class InitialActivity : AppCompatActivity() {
    lateinit var binding: ActivityInitialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GEO_LOCATION_REQUESTED_COD_SUCCESS && permissions.isNotEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Нам нужны гео данные")
                .setMessage("Пожалуйста, разрешите доступ к данным гео локации для продолжения работы")
                .setPositiveButton("OK") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        GEO_LOCATION_REQUESTED_COD_SUCCESS
                    )
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                        GEO_LOCATION_REQUESTED_COD_SUCCESS
                    )
                }
                .setNegativeButton("CANCEL") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }
}