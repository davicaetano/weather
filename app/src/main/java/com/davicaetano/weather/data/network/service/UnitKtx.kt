package com.davicaetano.weather.data.network.service

import com.davicaetano.weather.model.Imperial
import com.davicaetano.weather.model.Kelvin
import com.davicaetano.weather.model.Metric
import com.davicaetano.weather.model.UnitSystem

fun UnitSystem.getString(): String =
    when (this) {
        Imperial -> "imperial"
        Kelvin -> "standard"
        Metric -> "metric"
    }