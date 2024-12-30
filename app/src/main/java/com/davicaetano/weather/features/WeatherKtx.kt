package com.davicaetano.weather.features

import android.content.Context
import com.davicaetano.weather.R
import com.davicaetano.weather.features.weather.WindVS
import com.davicaetano.weather.model.Imperial
import com.davicaetano.weather.model.Kelvin
import com.davicaetano.weather.model.Metric
import com.davicaetano.weather.model.UnitSystem

fun String.toWeatherImageUrl(): String {
    return "https://openweathermap.org/img/wn/${this}@4x.png"
}

fun WindVS.getRotateAngle(): Float = (-45f - this.deg.toFloat())


fun WindVS.getSpeedText(
    context: Context,
    unitSystem: UnitSystem,
): String {
    return when (unitSystem) {
        Metric, Kelvin -> context.getString(R.string.speed_metric, this.speed.format("#"))
        Imperial -> context.getString(R.string.speed_imperial, this.speed.format("#"))
    }
}