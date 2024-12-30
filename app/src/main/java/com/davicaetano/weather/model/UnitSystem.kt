package com.davicaetano.weather.model

sealed class UnitSystem()
data object Kelvin: UnitSystem()
data object Metric: UnitSystem()
data object Imperial: UnitSystem()