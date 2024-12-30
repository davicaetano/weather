package com.davicaetano.weather.model

data class Location(
    var name: String,
    var lat: Double,
    var lon: Double,
    var country: String,
    var state: String,
)
