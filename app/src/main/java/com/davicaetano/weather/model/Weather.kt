package com.davicaetano.weather.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Weather constructor(
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
    val wind: Wind,
    val location: String,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val unit: Unit,
)
