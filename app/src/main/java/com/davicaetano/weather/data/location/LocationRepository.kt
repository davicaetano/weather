package com.davicaetano.weather.data.location

import com.davicaetano.weather.data.cache.WeatherCache
import com.davicaetano.weather.model.Coord
import com.davicaetano.weather.model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val weatherCache: WeatherCache
) {

    private val _locationState = MutableStateFlow<Coord?>(null)
    val locationState = _locationState.asStateFlow()

    val favoriteState = weatherCache.getLocations()

    fun setLocation(coord: Coord) {
        _locationState.value = coord
    }

    suspend fun saveFavorite(location: Location) {
        weatherCache.saveLocation(location)
    }

    suspend fun deleteFavorite(location: Location) {
        weatherCache.deleteLocation(location)
    }
}