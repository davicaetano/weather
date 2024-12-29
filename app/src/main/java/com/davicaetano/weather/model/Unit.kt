package com.davicaetano.weather.model

sealed class Unit()
data object Kelvin: Unit()
data object Metric: Unit()
data object Imperial: Unit()