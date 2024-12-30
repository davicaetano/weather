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

    private val _locationState = MutableStateFlow<LocationState>(InitialLocationState())
    val locationState = _locationState.asStateFlow()

    val favoriteState = weatherCache.getLocations()

    fun setLocation(locationState: LocationState) {
        _locationState.value = locationState
    }

    suspend fun saveFavorite(location: Location) {
        weatherCache.saveLocation(location)
    }

    suspend fun deleteFavorite(location: Location) {
        weatherCache.deleteLocation(location)
    }
}

sealed class LocationState()
class InitialLocationState(): LocationState()
class LoadingLocationState(): LocationState()
class SuccessLocationState(val coord: Coord): LocationState()
class DeniedLocationState(): LocationState()