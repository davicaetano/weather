package com.davicaetano.weather.features.main

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.davicaetano.weather.data.location.DeniedLocationState
import com.davicaetano.weather.data.location.LocationRepository
import com.davicaetano.weather.data.location.SuccessLocationState
import com.davicaetano.weather.model.Coord
import com.davicaetano.weather.ui.theme.WeatherTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("MissingPermission")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var locationRepository: LocationRepository

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            WeatherTheme {
                MainScreen(onRequestLocationClick = { askLocationPermission() })
            }
        }
        askLocationPermission()
    }

    private fun askLocationPermission() {
        if (isLocationPermissionGranted()) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                locationRepository.setLocation(
                    SuccessLocationState(Coord(it.latitude, it.longitude))
                )
            }
        }
    }

    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
            false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == 0) {
            if (grantResults.contains(PackageManager. PERMISSION_GRANTED)) {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    locationRepository.setLocation(
                        SuccessLocationState(Coord(it.latitude, it.longitude))
                    )
                }
            } else {
                locationRepository.setLocation(DeniedLocationState())
            }
        }
    }
}
