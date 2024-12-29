package com.davicaetano.weather.data.location

import com.davicaetano.weather.model.Coord
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(

) {
    fun getLocation(): Coord {
        return Coord(40.769228, -73.976420) // NY
    }
}