package com.davicaetano.weather.features.weather

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
import com.davicaetano.weather.model.Coord
import com.davicaetano.weather.model.Location
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

    val locationState = locationRepository.locationState
    val favoriteState = locationRepository.favoriteState

    val searchState = weatherRepository.searchState

    val weatherViewState = weatherRepository.weatherState.map {
        when (it) {
            is InitialWeatherState -> InitialWeatherViewState
            is LoadingWeatherState -> LoadingWeatherViewState
            is ErrorWeatherState -> ErrorWeatherViewState(it.error.toString())
            is SuccessWeatherState -> SuccessWeatherViewState(
                it.weather.toWeatherItemViewState(weatherFormatter)
            )
        }
    }

    val forecastViewState = weatherRepository.forecastState.map {
        when (it) {
            is InitialForecastState -> InitialForecastViewState
            is LoadingForecastState -> LoadingForecastViewState
            is ErrorForecastState -> ErrorForecastViewState(it.error.toString())
            is SuccessForecastState -> SuccessForecastViewState(it.weather.map {
                it.toForecastItemViewState(weatherFormatter)
            })
        }
    }

    fun fetchWeather(location: Location) {
        viewModelScope.launch {
            weatherRepository.fetchWeather(
                coord = Coord(lat = location.lat, lon = location.lon),
                unitSystem = unitRepository.getUnit()
            )
        }
    }

    fun fetchForecast(location: Location) {
        viewModelScope.launch {
            weatherRepository.fetchForecast(
                coord = Coord(lat = location.lat, lon = location.lon),
                unitSystem = unitRepository.getUnit()
            )
        }
    }

    fun onSearchChange(search: String) {
        weatherRepository.onSearchChange(search)
    }

    fun onSearchClick() {
        viewModelScope.launch {
            weatherRepository.onSearchClick()
        }
    }

    fun saveLocation(location: Location) {
        locationRepository.saveFavorite(location)
    }

    fun deleteLocation(location: Location) {
        locationRepository.deleteFavorite(location)
    }
}