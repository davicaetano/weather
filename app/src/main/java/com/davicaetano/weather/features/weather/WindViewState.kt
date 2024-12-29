package com.davicaetano.weather.features.weather

import com.davicaetano.weather.model.Wind
import com.davicaetano.weather.ui.WeatherFormatter
import java.math.BigDecimal

data class WindVS(
    val speed : String,
    val deg : BigDecimal,
)

fun Wind.toWindVS(
    weatherFormatter: WeatherFormatter,
): WindVS {
    return WindVS(
        speed = weatherFormatter.getClouds(this.speed),
        deg = this.deg,
    )
}
