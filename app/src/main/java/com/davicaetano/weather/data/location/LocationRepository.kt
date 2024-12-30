package com.davicaetano.weather.data.location

import com.davicaetano.weather.model.Coord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(

) {

    private val _locationState = MutableStateFlow<Coord?>(null)
    val locationState = _locationState.asStateFlow()

    fun setLocation(coord: Coord) {
        _locationState.value = coord
    }

    fun getNyLocation(): Coord {
        return Coord(40.769228, -73.976420) // NY
    }
}