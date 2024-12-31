package com.davicaetano.weather.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Forecast(
    val lat: Double,
    val lon: Double,
    val temp: BigDecimal,
    val date: LocalDateTime,
    val description: String,
    val icon: String,
    val feelsLike: BigDecimal,
    val high: BigDecimal,
    val low: BigDecimal,
    val pressure: BigDecimal,
    val humidity: BigDecimal,
    val visibility: BigDecimal,
    val clouds: BigDecimal,
    val windSpeed: BigDecimal,
    val windDeg: BigDecimal,
    val location: String,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val unitSystem: UnitSystem,
)

fun getForecast(): Forecast {
    return Forecast(
        lat = 0.0,
        lon = 0.0,
        temp = BigDecimal.ZERO,
        date = LocalDateTime.now(),
        description = "",
        icon = "",
        feelsLike = BigDecimal.ZERO,
        high = BigDecimal.ZERO,
        low = BigDecimal.ZERO,
        pressure = BigDecimal.ZERO,
        humidity = BigDecimal.ZERO,
        visibility = BigDecimal.ZERO,
        clouds = BigDecimal.ZERO,
        windSpeed = BigDecimal.ZERO,
        windDeg = BigDecimal.ZERO,
        location = "",
        sunrise = LocalDateTime.now(),
        sunset = LocalDateTime.now(),
        unitSystem = Imperial,
    )
}
