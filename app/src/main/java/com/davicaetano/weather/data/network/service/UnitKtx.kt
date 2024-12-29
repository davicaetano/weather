package com.davicaetano.weather.data.network.service

import com.davicaetano.weather.model.Imperial
import com.davicaetano.weather.model.Kelvin
import com.davicaetano.weather.model.Metric
import com.davicaetano.weather.model.Unit

fun Unit.getString(): String =
    when (this) {
        Imperial -> "imperial"
        Kelvin -> "standard"
        Metric -> "metric"
    }