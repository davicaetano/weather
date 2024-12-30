package com.davicaetano.weather.features.weather

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.davicaetano.weather.model.Weather
import com.davicaetano.weather.ui.WeatherFormatter
import com.davicaetano.weather.features.toWeatherImageUrl
import com.davicaetano.weather.model.UnitSystem

sealed class WeatherViewState(open val weatherItemViewState: WeatherItemViewState?)
object InitialWeatherViewState : WeatherViewState(null)
data class LoadingWeatherViewState(override val weatherItemViewState: WeatherItemViewState?) :
    WeatherViewState(weatherItemViewState)

data class SuccessWeatherViewState(override val weatherItemViewState: WeatherItemViewState) :
    WeatherViewState(weatherItemViewState)

data class ErrorWeatherViewState(
    override val weatherItemViewState: WeatherItemViewState?,
    val error: String
) : WeatherViewState(weatherItemViewState)

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
    val windSpeed: String,
    val windDeg: String,
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
        temp = weatherFormatter.getFormattedBigDecimal(this.temp),
        iconUrl = this.icon.toWeatherImageUrl(),
        feelsLike = weatherFormatter.getFormattedBigDecimal(this.feelsLike),
        high = weatherFormatter.getFormattedBigDecimal(this.high),
        low = weatherFormatter.getFormattedBigDecimal(this.low),
        pressure = weatherFormatter.getFormattedBigDecimal(this.pressure),
        humidity = weatherFormatter.getFormattedBigDecimal(this.pressure),
        visibility = weatherFormatter.getFormattedBigDecimal(this.clouds),
        clouds = weatherFormatter.getFormattedBigDecimal(this.clouds),
        windSpeed = weatherFormatter.getFormattedBigDecimal(this.windSpeed),
        windDeg = weatherFormatter.getFormattedBigDecimal(this.windDeg),
        sunrise = weatherFormatter.getTime(this.sunrise),
        sunset = weatherFormatter.getTime(this.sunset),
        unitSystem = this.unitSystem,
    )
}

