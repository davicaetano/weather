package com.davicaetano.weather.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Forecast(
    val temp: BigDecimal,
    val date: LocalDateTime,
    val description: String,
    val icon: String,
    val clouds: BigDecimal,
    val wind: Wind,
)
