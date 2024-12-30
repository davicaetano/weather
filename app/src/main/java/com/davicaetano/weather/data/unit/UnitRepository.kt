package com.davicaetano.weather.data.unit

import com.davicaetano.weather.model.Imperial
import com.davicaetano.weather.model.UnitSystem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnitRepository @Inject constructor(

) {

    fun getUnit(): UnitSystem {
        return Imperial
    }
}