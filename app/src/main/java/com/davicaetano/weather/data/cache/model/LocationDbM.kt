package com.davicaetano.weather.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.davicaetano.weather.model.Location

@Entity(tableName = "location")
data class LocationDbM(
    @PrimaryKey var id: String,
    var name: String = "",
    var lat: Double,
    var lon: Double,
    var country: String = "",
    var state: String = "",
) {
    fun toLocation(): Location {
        return Location(
            name = this.name,
            lat = this.lat,
            lon = this.lon,
            country = this.country,
            state = this.state
        )
    }
}
fun Location.toLocationDbM(): LocationDbM {
    return LocationDbM(
        id = "${this.lat} - ${this.lon}",
        name = this.name,
        lat = this.lat,
        lon = this.lon,
        state = this.state,
        country = this.country,

        )
}