package com.davicaetano.weather.data.location

import com.davicaetano.weather.data.ErrorSearchState
import com.davicaetano.weather.data.InitialSearchState
import com.davicaetano.weather.data.LoadingSearchState
import com.davicaetano.weather.data.SearchState
import com.davicaetano.weather.data.SuccessSearchState
import com.davicaetano.weather.data.cache.WeatherCache
import com.davicaetano.weather.data.network.service.WeatherApiService
import com.davicaetano.weather.model.Coord
import com.davicaetano.weather.model.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val weatherCache: WeatherCache
) {

    private val _searchState = MutableStateFlow<SearchState>(InitialSearchState())
    val searchState = _searchState.asStateFlow()

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

    private suspend fun fetchGeoLocation(
        query: String,
    ) {
        _searchState.value = LoadingSearchState(query)
        try {
            val result = weatherApiService.getLocationData(query)
            if (result.isSuccessful) {
                _searchState.value = SuccessSearchState(
                    searchField = query,
                    locationList = result.body()!!.map { it.toLocation() }
                )
            } else {
                _searchState.value =
                    ErrorSearchState(error = Throwable(result.errorBody().toString()))
            }
        } catch (error: Throwable) {
            _searchState.value = ErrorSearchState(error = error)
        }
    }

    fun onSearchChange(search: String) {
        _searchState.value = InitialSearchState(search)
    }

    suspend fun fetchSearch() {
        fetchGeoLocation(searchState.value.searchField.trim())
    }
}

sealed class LocationState()
class InitialLocationState(): LocationState()
class LoadingLocationState(): LocationState()
class SuccessLocationState(val coord: Coord): LocationState()
class DeniedLocationState(): LocationState()