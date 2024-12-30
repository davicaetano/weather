package com.davicaetano.weather.features.weather

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.davicaetano.weather.features.toWeatherImageUrl
import com.davicaetano.weather.model.Forecast
import com.davicaetano.weather.model.UnitSystem
import com.davicaetano.weather.ui.WeatherFormatter


sealed class ForecastViewState(open val list: List<ForecastItemViewState>?)
class InitialForecastViewState() : ForecastViewState(null)

class LoadingForecastViewState(override val list: List<ForecastItemViewState>?) :
    ForecastViewState(list)

class SuccessForecastViewState(override val list: List<ForecastItemViewState>) :
    ForecastViewState(list)

class ErrorForecastViewState(override val list: List<ForecastItemViewState>?, val error: String) :
    ForecastViewState(list)

data class ForecastItemViewState(
    val toolbarTitle: String,
    val title: String,
    val date: String,
    val temp: String,
    val iconUrl: String,
    val feelsLike: String,
    val high: String,
    val low: String,
    val pressure: String,
    val humidity: String,
    val visibility: String,
    val clouds: String,
    val sunrise: String,
    val sunset: String,
    val unitSystem: UnitSystem,

    val dayOfWeek: String,
    val shortHour: String,

    )

fun Forecast.toForecastItemViewState(
    weatherFormatter: WeatherFormatter,
): ForecastItemViewState {
    return ForecastItemViewState(
        toolbarTitle = this.location,
        title = this.description.capitalize(Locale.current),
        date = weatherFormatter.getTime(this.date),
        temp = weatherFormatter.getFormattedBigDecimal(this.temp),
        iconUrl = this.icon.toWeatherImageUrl(),
        feelsLike = weatherFormatter.getFormattedBigDecimal(this.feelsLike),
        high = weatherFormatter.getFormattedBigDecimal(this.high),
        low = weatherFormatter.getFormattedBigDecimal(this.low),
        pressure = weatherFormatter.getFormattedBigDecimal(this.pressure),
        humidity = weatherFormatter.getFormattedBigDecimal(this.pressure),
        visibility = weatherFormatter.getFormattedBigDecimal(this.clouds),
        clouds = weatherFormatter.getFormattedBigDecimal(this.clouds),
        sunrise = weatherFormatter.getTime(this.sunrise),
        sunset = weatherFormatter.getTime(this.sunset),
        unitSystem = this.unitSystem,
        dayOfWeek = weatherFormatter.getShortDayOfTheWeek(this.date),
        shortHour = weatherFormatter.getShortHour(this.date)
    )
}
