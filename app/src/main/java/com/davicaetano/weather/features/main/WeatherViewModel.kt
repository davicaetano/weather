package com.davicaetano.weather.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davicaetano.weather.data.ErrorForecastState
import com.davicaetano.weather.data.ErrorWeatherState
import com.davicaetano.weather.data.InitialForecastState
import com.davicaetano.weather.data.InitialWeatherState
import com.davicaetano.weather.data.LoadingForecastState
import com.davicaetano.weather.data.LoadingWeatherState
import com.davicaetano.weather.data.SuccessForecastState
import com.davicaetano.weather.data.SuccessWeatherState
import com.davicaetano.weather.data.WeatherRepository
import com.davicaetano.weather.data.location.LocationRepository
import com.davicaetano.weather.data.unit.UnitRepository
import com.davicaetano.weather.features.weather.ErrorForecastViewState
import com.davicaetano.weather.features.weather.ErrorWeatherViewState
import com.davicaetano.weather.features.weather.InitialForecastViewState
import com.davicaetano.weather.features.weather.InitialWeatherViewState
import com.davicaetano.weather.features.weather.LoadingForecastViewState
import com.davicaetano.weather.features.weather.LoadingWeatherViewState
import com.davicaetano.weather.features.weather.SuccessForecastViewState
import com.davicaetano.weather.features.weather.SuccessWeatherViewState
import com.davicaetano.weather.features.weather.toForecastItemViewState
import com.davicaetano.weather.features.weather.toWeatherItemViewState
import com.davicaetano.weather.model.Coord
import com.davicaetano.weather.model.Location
import com.davicaetano.weather.ui.WeatherFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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

    var jobList: MutableList<Job> = mutableListOf()

    val locationState = locationRepository.locationState
    val favoriteState = locationRepository.favoriteState

    val searchState = weatherRepository.searchState

    val weatherViewState = weatherRepository.weatherState.map {
        when (it) {
            is InitialWeatherState -> InitialWeatherViewState
            is LoadingWeatherState -> LoadingWeatherViewState(
                it.weather?.toWeatherItemViewState(weatherFormatter)
            )

            is ErrorWeatherState -> ErrorWeatherViewState(
                it.weather?.toWeatherItemViewState(weatherFormatter),
                it.error.toString()
            )

            is SuccessWeatherState -> SuccessWeatherViewState(
                it.weather.toWeatherItemViewState(weatherFormatter)
            )
        }
    }

    val forecastViewState = weatherRepository.forecastState.map {
        when (it) {
            is InitialForecastState -> InitialForecastViewState()
            is LoadingForecastState -> LoadingForecastViewState(
                it.forecast?.map { it.toForecastItemViewState(weatherFormatter) }
            )

            is ErrorForecastState -> ErrorForecastViewState(
                it.forecast?.map { it.toForecastItemViewState(weatherFormatter) },
                    it.error.toString()
            )
            is SuccessForecastState -> SuccessForecastViewState(
                it.forecast.map { it.toForecastItemViewState(weatherFormatter) }
            )
        }
    }

    fun fetchWeather(location: Location, isLocal: Boolean) {
        viewModelScope.launch {
            weatherRepository.fetchWeather(
                coord = Coord(lat = location.lat, lon = location.lon),
                unitSystem = unitRepository.getUnit(),
                isLocal = isLocal
            )
        }.let { jobList.add(it) }
    }

    fun fetchForecast(location: Location, isLocal: Boolean) {
        viewModelScope.launch {
            weatherRepository.fetchForecast(
                coord = Coord(lat = location.lat, lon = location.lon),
                unitSystem = unitRepository.getUnit(),
                isLocal
            )
        }.let { jobList.add(it) }
    }

    fun onSearchChange(search: String) {
        weatherRepository.onSearchChange(search)
    }

    fun onSearchClick() {
        viewModelScope.launch {
            weatherRepository.onSearchClick()
        }.let { jobList.add(it) }
    }

    fun saveLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.saveFavorite(location)
        }
    }

    fun deleteLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.deleteFavorite(location)
        }
    }

    fun cancelJobs() {
        jobList.forEach { it.cancel() }
        jobList = mutableListOf()
    }
}