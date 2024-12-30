package com.davicaetano.weather.data.cache

import com.davicaetano.weather.data.cache.db.WeatherDao
import com.davicaetano.weather.data.cache.model.LocationDbM
import com.davicaetano.weather.data.cache.model.WeatherDbM
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
        return weatherDao.getWeather().map {
            it.map {
                Weather(
                    lat = it.lat,
                    lon = it.lon,
                    temp = it.temp,
                    date = it.date,
                    description = it.description,
                    icon = it.icon,
                    feelsLike = it.feelsLike,
                    high = it.high,
                    low = it.low,
                    pressure = it.pressure,
                    humidity = it.humidity,
                    visibility = it.visibility,
                    clouds = it.clouds,
                    windSpeed = it.windSpeed,
                    windDeg = it.windDeg,
                    location = it.location,
                    sunrise = it.sunrise,
                    sunset = it.sunset,
                    unitSystem = it.unitSystem,

                )
            }

        }
    }
    
    suspend fun saveWeather(weather: Weather) {
        weatherDao.insertWeather(
            WeatherDbM(
                lat = weather.lat,
                lon = weather.lon,
                temp = weather.temp,
                date = weather.date,
                description = weather.description,
                icon = weather.icon,
                feelsLike = weather.feelsLike,
                high = weather.high,
                low = weather.low,
                pressure = weather.pressure,
                humidity = weather.humidity,
                visibility = weather.visibility,
                clouds = weather.clouds,
                windSpeed = weather.windSpeed,
                windDeg = weather.windDeg,
                location = weather.location,
                sunrise = weather.sunrise,
                sunset = weather.sunset,
                unitSystem = weather.unitSystem,
            )
        )
    }

    fun getLocations(): Flow<List<Location>> {
        return weatherDao.getLocation()
            .map {
                it.map {
                    Location(
                        name = it.name,
                        lat = it.lat,
                        lon = it.lon,
                        country = it.country,
                        state = it.state
                    )
                }
            }
    }

    suspend fun saveLocation(location: Location) {
        weatherDao.insertLocation(
            LocationDbM(
                id = "${location.lat} - ${location.lon}",
                name = location.name,
                lat = location.lat,
                lon = location.lon,
                state = location.state,
                country = location.country,

            )
        )
    }

    suspend fun deleteLocation(location: Location) {
        weatherDao.deleteLocation(
            LocationDbM(
                id = "${location.lat} - ${location.lon}",
                name = location.name,
                lat = location.lat,
                lon = location.lon,
                state = location.state,
                country = location.country,

                )
        )
    }
}