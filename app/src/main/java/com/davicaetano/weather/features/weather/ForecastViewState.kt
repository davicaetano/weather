package com.davicaetano.weather.features.weather

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.davicaetano.weather.features.toWeatherImageUrl
import com.davicaetano.weather.model.Forecast
import com.davicaetano.weather.model.UnitSystem
import com.davicaetano.weather.ui.WeatherFormatter


sealed class ForecastViewState()
object InitialForecastViewState : ForecastViewState()
object LoadingForecastViewState : ForecastViewState()
data class SuccessForecastViewState(val forecastItemViewStateList: List<ForecastItemViewState>) : ForecastViewState()
data class ErrorForecastViewState(val error: String) : ForecastViewState()

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
    val wind: WindVS,
    val clouds: String,
    val sunrise: String,
    val sunset: String,
    val unitSystem: UnitSystem,

    val dayOfWeek: String,
    val shortHour: String,

//    val forecastVS: ForecastVS,
)
fun Forecast.toForecastItemViewState(
    weatherFormatter: WeatherFormatter,
): ForecastItemViewState {
    return ForecastItemViewState(
        toolbarTitle = this.location,
        title = this.description.capitalize(Locale.current),
        date = weatherFormatter.getTime(this.date),
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
        dayOfWeek = weatherFormatter.getShortDayOfTheWeek(this.date),
        shortHour = weatherFormatter.getShortHour(this.date)
    )
}
