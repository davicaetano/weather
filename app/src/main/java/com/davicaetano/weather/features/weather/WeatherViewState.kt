package com.davicaetano.weather.features.weather

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.davicaetano.weather.model.Weather
import com.davicaetano.weather.ui.WeatherFormatter
import com.davicaetano.weather.features.toWeatherImageUrl
import com.davicaetano.weather.model.UnitSystem

sealed class WeatherViewState()
object InitialWeatherViewState: WeatherViewState()
object LoadingWeatherViewState: WeatherViewState()
data class SuccessWeatherViewState(val weatherItemViewState: WeatherItemViewState): WeatherViewState()
data class ErrorWeatherViewState(val error: String): WeatherViewState()

data class WeatherItemViewState(
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
    val sunrise: String,
    val sunset: String,
    val unitSystem: UnitSystem,

//    val forecastVS: ForecastVS,
)
fun Weather.toWeatherItemViewState(
    weatherFormatter: WeatherFormatter,
): WeatherItemViewState {
    return WeatherItemViewState(
        toolbarTitle = this.location,
        title = this.description.capitalize(Locale.current),
        temp = weatherFormatter.getTempWithoutDecimal(this.temp),
        iconUrl = this.icon.toWeatherImageUrl(),
        feelsLike = weatherFormatter.getTempWithoutDecimal(this.feelsLike),
        high = weatherFormatter.getTempWithoutDecimal(this.high),
        low = weatherFormatter.getTempWithoutDecimal(this.low),
        pressure = weatherFormatter.getPressure(this.pressure),
        humidity = weatherFormatter.getHumidity(this.pressure),
        visibility = weatherFormatter.getClouds(this.clouds),
        clouds = weatherFormatter.getClouds(this.clouds),
        wind = this.wind.toWindVS(weatherFormatter),
        sunrise = weatherFormatter.getTime(this.sunrise),
        sunset = weatherFormatter.getTime(this.sunset),
        unitSystem = this.unitSystem,
    )
}

