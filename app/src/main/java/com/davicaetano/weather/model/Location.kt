package com.davicaetano.weather.model

data class Location(
    var name: String = "",
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var country: String = "",
    var state: String = "",
)
