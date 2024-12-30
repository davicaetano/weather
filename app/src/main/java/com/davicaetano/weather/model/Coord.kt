package com.davicaetano.weather.model

import kotlinx.serialization.Serializable

@Serializable
class Coord (
    val lat : Double,
    val lon : Double
)