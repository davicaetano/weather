package com.davicaetano.weather.features.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davicaetano.weather.data.ErrorWeatherState
import com.davicaetano.weather.data.InitialWeatherState
import com.davicaetano.weather.data.LoadingWeatherState
import com.davicaetano.weather.data.SuccessWeatherState
import com.davicaetano.weather.data.WeatherRepository
import com.davicaetano.weather.data.location.LocationRepository
import com.davicaetano.weather.data.unit.UnitRepository
import com.davicaetano.weather.ui.WeatherFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val weatherFormatter: WeatherFormatter,
    private val locationRepository: LocationRepository,
    private val unitRepository: UnitRepository,
) : ViewModel() {

    val weatherViewState = weatherRepository.weatherData.map {
        when (it) {
            is InitialWeatherState -> InitialWeatherViewState
            is LoadingWeatherState -> LoadingWeatherViewState
            is ErrorWeatherState -> ErrorWeatherViewState(it.error.toString())
            is SuccessWeatherState -> SuccessWeatherViewState(
                it.weather.toWeatherVS(weatherFormatter)
            )
        }
    }

    fun fetch() {
        viewModelScope.launch {
            weatherRepository.fetchWeather(
                coord = locationRepository.getLocation(),
                unit = unitRepository.getUnit()
            )
        }
    }
}