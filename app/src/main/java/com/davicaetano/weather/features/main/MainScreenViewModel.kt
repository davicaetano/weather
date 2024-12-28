package com.davicaetano.weather.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davicaetano.weather.data.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val weatherRepository: WeatherRepository
) : ViewModel() {

    val data = weatherRepository.weatherData

    fun fetch () {
        viewModelScope.launch {
            weatherRepository.fetch()
        }
    }
}