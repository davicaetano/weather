package com.davicaetano.weather.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationDbM(
    @PrimaryKey var id: String,
    var name: String = "",
    var lat: Double,
    var lon: Double,
    var country: String = "",
    var state: String = "",
)