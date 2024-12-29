package com.davicaetano.weather.model

import java.math.BigDecimal

data class Wind constructor (
    val speed : BigDecimal,
    val deg : BigDecimal,
    val gust : BigDecimal?,
)