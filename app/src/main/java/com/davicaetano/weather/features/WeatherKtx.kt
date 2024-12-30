package com.davicaetano.weather.features

import android.content.Context
import com.davicaetano.weather.R
import com.davicaetano.weather.features.weather.WeatherItemViewState
import com.davicaetano.weather.model.Imperial
import com.davicaetano.weather.model.Kelvin
import com.davicaetano.weather.model.Metric
import com.davicaetano.weather.model.UnitSystem

fun String.toWeatherImageUrl(): String {
    return "https://openweathermap.org/img/wn/${this}@4x.png"
}

fun WeatherItemViewState.getRotateAngle(): Float = (-45f - this.windDeg.toFloat())

fun WeatherItemViewState.getSpeedText(
    context: Context,
    unitSystem: UnitSystem,
): String {
    return when (unitSystem) {
        Metric, Kelvin -> context.getString(R.string.speed_metric, this.windSpeed.format("#"))
        Imperial -> context.getString(R.string.speed_imperial, this.windSpeed.format("#"))
    }
}