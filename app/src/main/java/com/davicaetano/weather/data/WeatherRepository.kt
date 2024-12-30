package com.davicaetano.weather.data

import com.davicaetano.weather.data.cache.WeatherCache
import com.davicaetano.weather.data.network.service.NetworkServiceSettings
import com.davicaetano.weather.data.network.service.WeatherApiService
import com.davicaetano.weather.data.network.service.getString
import com.davicaetano.weather.model.Coord
import com.davicaetano.weather.model.Forecast
import com.davicaetano.weather.model.Location
import com.davicaetano.weather.model.UnitSystem
import com.davicaetano.weather.model.Weather
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    networkServiceSettings: NetworkServiceSettings,
    private val weatherCache: WeatherCache
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
        isLocal: Boolean,
    ) {
        val cache: Weather? = weatherCache.getWeather()
            .map {
                it.filter {
                    if (isLocal) {
                        it.lat == 0.0 && it.lon == 0.0
                    } else {
                        it.lat == coord.lat && it.lon == coord.lon
                    }
                }
            }
            .map { it.firstOrNull() }.first()

        _weatherState.value = LoadingWeatherState(cache)
        delay(3000)
        try {
            val result = weatherApiService.getWeatherData(
                lat = coord.lat,
                lon = coord.lon,
                unit = unitSystem.getString()
            )
            if (result.isSuccessful) {
                val weather = result.body()!!
                    .toWeather(lat = coord.lat, lon = coord.lon, unitSystem = unitSystem)
                weatherCache.saveWeather(weather, isLocal)
                _weatherState.value = SuccessWeatherState(weather)
            } else {
                _weatherState.value = ErrorWeatherState(
                    cache,
                    Throwable(result.errorBody().toString())
                )
            }
        } catch (error: Throwable) {
            _weatherState.value = ErrorWeatherState(cache, error)
        }
    }

    suspend fun fetchForecast(
        coord: Coord,
        unitSystem: UnitSystem,
        isLocal: Boolean,
    ) {
        val cache: List<Forecast>? = weatherCache.getForecast()
            .map {
                it.filter {
                    if (isLocal) {
                        it.lat == 0.0 && it.lon == 0.0
                    } else {
                        it.lat == coord.lat && it.lon == coord.lon
                    }
                }.filter {
                    it.date >= LocalDateTime.now()
                }.sortedBy { it.date }
            }
            .firstOrNull()

        _forecastState.value = LoadingForecastState(cache)
        delay(3000)
        try {
            val result = weatherApiService.getForecastData(
                lat = coord.lat,
                lon = coord.lon,
                unit = unitSystem.getString()
            )
            if (result.isSuccessful) {
                val forecastList =                         result.body()!!.toForecastList(
                    coord.lat,
                    coord.lon,
                    unitSystem
                )
                weatherCache.saveForecastList(forecastList, isLocal)
                _forecastState.value = SuccessForecastState(forecastList)
            } else {
                _forecastState.value = ErrorForecastState(
                    cache,
                    Throwable(result.errorBody().toString())
                )
            }
        } catch (error: Throwable) {
            _forecastState.value = ErrorForecastState(cache, error)
        }
    }

    suspend fun fetchGeoLocation(
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

    suspend fun onSearchClick() {
        fetchGeoLocation(searchState.value.searchField.trim())
    }
}

sealed class WeatherState(open val weather: Weather?)
class InitialWeatherState() : WeatherState(null)
class LoadingWeatherState(override val weather: Weather?) : WeatherState(weather)
class SuccessWeatherState(override val weather: Weather) : WeatherState(weather)
class ErrorWeatherState(override val weather: Weather?, val error: Throwable) :
    WeatherState(weather)

sealed class ForecastState(open val forecast: List<Forecast>?)
class InitialForecastState() : ForecastState(null)
class LoadingForecastState(override val forecast: List<Forecast>?) : ForecastState(forecast)
class SuccessForecastState(override val forecast: List<Forecast>) : ForecastState(forecast)
class ErrorForecastState(override val forecast: List<Forecast>?, val error: Throwable) :
    ForecastState(forecast)


sealed class SearchState(open val searchField: String = "")
class InitialSearchState(override val searchField: String = "") : SearchState()
class LoadingSearchState(override val searchField: String = "") : SearchState(searchField)
class SuccessSearchState(override val searchField: String = "", val locationList: List<Location>) :
    SearchState()

class ErrorSearchState(override val searchField: String = "", val error: Throwable) : SearchState()

