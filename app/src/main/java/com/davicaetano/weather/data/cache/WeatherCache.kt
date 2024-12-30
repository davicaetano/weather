package com.davicaetano.weather.data.cache

import com.davicaetano.weather.data.cache.db.WeatherDao
import com.davicaetano.weather.data.cache.model.toForecastDbM
import com.davicaetano.weather.data.cache.model.toLocationDbM
import com.davicaetano.weather.data.cache.model.toWeatherDbM
import com.davicaetano.weather.model.Forecast
import com.davicaetano.weather.model.Location
import com.davicaetano.weather.model.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherCache @Inject constructor(
    private val weatherDao: WeatherDao
) {

    fun getWeather(): Flow<List<Weather>> {
        return weatherDao.getWeather().map { it.map { it.toWeather() } }
    }

    suspend fun saveWeather(weather: Weather, isLocal: Boolean) {
        weatherDao.insertWeather(weather.toWeatherDbM(isLocal))
    }

    fun getForecast(): Flow<List<Forecast>> {
        return weatherDao.getForecast().map { it.map { it.toForecast() } }
    }

    suspend fun saveForecast(forecast: Forecast, isLocal: Boolean) {
        weatherDao.insertForecast(forecast.toForecastDbM(isLocal))
    }

    suspend fun saveForecastList(forecastList: List<Forecast>, isLocal: Boolean) {
        forecastList.forEach {
            weatherDao.insertForecast(it.toForecastDbM(isLocal))
        }
    }


    fun getLocations(): Flow<List<Location>> {
        return weatherDao.getLocation().map { it.map { it.toLocation() } }
    }

    suspend fun saveLocation(location: Location) {
        weatherDao.insertLocation(location.toLocationDbM())
    }

    suspend fun deleteLocation(location: Location) {
        weatherDao.deleteLocation(location.toLocationDbM())
    }
}