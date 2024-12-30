package com.davicaetano.weather.data

import com.davicaetano.weather.data.network.service.NetworkServiceSettings
import com.davicaetano.weather.data.network.service.WeatherApiService
import com.davicaetano.weather.data.network.service.getString
import com.davicaetano.weather.model.Coord
import com.davicaetano.weather.model.Forecast
import com.davicaetano.weather.model.Location
import com.davicaetano.weather.model.UnitSystem
import com.davicaetano.weather.model.Weather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    networkServiceSettings: NetworkServiceSettings,
) {

    private val weatherApiService: WeatherApiService = networkServiceSettings.feedNetworkService

    private val _weatherState = MutableStateFlow<WeatherState>(InitialWeatherState())
    val weatherState = _weatherState.asStateFlow()

    private val _forecastState = MutableStateFlow<ForecastState>(InitialForecastState())
    val forecastState = _forecastState.asStateFlow()

    private val _searchState = MutableStateFlow<SearchState>(InitialSearchState())
    val searchState = _searchState.asStateFlow()

    suspend fun fetchWeather(
        coord: Coord,
        unitSystem: UnitSystem,
    ) {
        _weatherState.value = LoadingWeatherState()
        try {
            val result = weatherApiService.getWeatherData(
                lat = coord.lat,
                lon = coord.lon,
                unit = unitSystem.getString()
            )
            if (result.isSuccessful) {
                _weatherState.value = SuccessWeatherState(result.body()!!.toWeather(unitSystem))
            } else {
                _weatherState.value = ErrorWeatherState(Throwable(result.errorBody().toString()))
            }
        } catch (error: Throwable) {
            _weatherState.value = ErrorWeatherState(error)
        }
    }

    suspend fun fetchForecast(
        coord: Coord,
        unitSystem: UnitSystem,
    ) {
        _forecastState.value = LoadingForecastState()
        try {
            val result = weatherApiService.getForecastData(
                lat = coord.lat,
                lon = coord.lon,
                unit = unitSystem.getString()
            )
            if (result.isSuccessful) {
                _forecastState.value = SuccessForecastState(result.body()!!.toForecastList(unitSystem))
            } else {
                _forecastState.value = ErrorForecastState(Throwable(result.errorBody().toString()))
            }
        } catch (error: Throwable) {
            _forecastState.value = ErrorForecastState(error)
        }
    }

    suspend fun fetchGeoLocation(
        query: String,
    ) {
        _searchState.value = LoadingSearchState()
        try {
            val result = weatherApiService.getLocationData(query)
            if (result.isSuccessful) {
                _searchState.value = SuccessSearchState(locationList = result.body()!!.map { it.toLocation() })
            } else {
                _searchState.value = ErrorSearchState(error = Throwable(result.errorBody().toString()))
            }
        } catch (error: Throwable) {
            _searchState.value = ErrorSearchState(error = error)
        }
    }

    fun onSearchChange(search: String) {
        val state = _searchState.value
        _searchState.value = when(state) {
            is ErrorSearchState -> state.copy(searchField = search)
            is InitialSearchState -> state.copy(searchField = search)
            is LoadingSearchState -> state.copy(searchField = search)
            is SuccessSearchState -> state.copy(searchField = search)
        }
    }

    suspend fun onSearchClick() {
        fetchGeoLocation(searchState.value.searchField)
    }
}

sealed class WeatherState()
class InitialWeatherState() : WeatherState()
class LoadingWeatherState() : WeatherState()
class SuccessWeatherState(val weather: Weather) : WeatherState()
class ErrorWeatherState(val error: Throwable) : WeatherState()

sealed class ForecastState()
class InitialForecastState() : ForecastState()
class LoadingForecastState() : ForecastState()
class SuccessForecastState(val weather: List<Forecast>) : ForecastState()
class ErrorForecastState(val error: Throwable) : ForecastState()


sealed class SearchState(open val searchField: String = "")
data class InitialSearchState(override val searchField: String = "") : SearchState()
data class LoadingSearchState(override val searchField: String = "") : SearchState(searchField)
data class SuccessSearchState(override val searchField: String = "", val locationList: List<Location>): SearchState()
data class ErrorSearchState(override val searchField: String = "", val error: Throwable) : SearchState()

