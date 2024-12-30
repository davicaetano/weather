package com.davicaetano.weather.model

import kotlinx.serialization.Serializable

@Serializable
class Coord (
    val lat : Double = 0.0,
    val lon : Double = 0.0
)