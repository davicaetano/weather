package com.davicaetano.weather.data

import com.davicaetano.weather.data.network.model.WeatherDataResult
import com.davicaetano.weather.data.network.service.NetworkServiceSettings
import com.davicaetano.weather.data.network.service.WeatherApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    networkServiceSettings: NetworkServiceSettings,
) {

    private val weatherApiService: WeatherApiService = networkServiceSettings.feedNetworkService

    private val _weatherData = MutableStateFlow<WeatherResult>(InitialWeatherResult())
    val weatherData = _weatherData.asStateFlow()

    suspend fun fetch() {
        _weatherData.value = LoadingWeatherResult()
        try {
            val result = weatherApiService.getWeatherData(
                lat = 39.186688f,
                lon = -96.566600f,
            )
            if (result.isSuccessful) {
                _weatherData.value = SuccessWeatherResult(result.body()!!)
            } else {
                _weatherData.value = ErrorWeatherResult(Throwable(result.errorBody().toString()))
            }
        }catch (error: Throwable) {
            _weatherData.value = ErrorWeatherResult(error)
        }
    }
}

sealed class WeatherResult()
class InitialWeatherResult(): WeatherResult()
class LoadingWeatherResult(): WeatherResult()
class SuccessWeatherResult(val result: WeatherDataResult): WeatherResult()
class ErrorWeatherResult(val error: Throwable): WeatherResult()