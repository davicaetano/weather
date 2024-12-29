package com.davicaetano.weather.data

import com.davicaetano.weather.data.network.service.NetworkServiceSettings
import com.davicaetano.weather.data.network.service.WeatherApiService
import com.davicaetano.weather.data.network.service.getString
import com.davicaetano.weather.model.Coord
import com.davicaetano.weather.model.Unit
import com.davicaetano.weather.model.Weather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    networkServiceSettings: NetworkServiceSettings,
) {

    private val weatherApiService: WeatherApiService = networkServiceSettings.feedNetworkService

    private val _weatherData = MutableStateFlow<WeatherState>(InitialWeatherState())
    val weatherData = _weatherData.asStateFlow()

    suspend fun fetchWeather(
        coord: Coord,
        unit: Unit,
    ) {
        _weatherData.value = LoadingWeatherState()
        try {
            val result = weatherApiService.getWeatherData(
                lat = coord.lat,
                lon = coord.lon,
                unit = unit.getString()
            )
            if (result.isSuccessful) {
                _weatherData.value = SuccessWeatherState(result.body()!!.toWeather(unit))
            } else {
                _weatherData.value = ErrorWeatherState(Throwable(result.errorBody().toString()))
            }
        } catch (error: Throwable) {
            _weatherData.value = ErrorWeatherState(error)
        }
    }
}

sealed class WeatherState()
class InitialWeatherState() : WeatherState()
class LoadingWeatherState() : WeatherState()
class SuccessWeatherState(val weather: Weather) : WeatherState()
class ErrorWeatherState(val error: Throwable) : WeatherState()
