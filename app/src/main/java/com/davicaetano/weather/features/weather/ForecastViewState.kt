package com.davicaetano.weather.features.weather


sealed class ForecastViewState()
object InitialForecastViewState : ForecastViewState()
object LoadingForecastViewState : ForecastViewState()
data class SuccessForecastViewState(val forecastVSList: List<ForecastVS>) : ForecastViewState()
data class ErrorForecastViewState(val error: String) : ForecastViewState()

data class ForecastVS(
    val toolbarTitle: String,
    val title: String,
    val temp: String,
    val iconUrl: String,
    val feelsLike: String,
    val high: String,
    val low: String,
    val pressure: String,
    val humidity: String,
    val visibility: String,
    val wind: WindVS,
    val clouds: String,
)