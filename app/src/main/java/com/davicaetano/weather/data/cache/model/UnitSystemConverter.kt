package com.davicaetano.weather.data.cache.model

import androidx.room.TypeConverter
import com.davicaetano.weather.model.Imperial
import com.davicaetano.weather.model.Kelvin
import com.davicaetano.weather.model.Metric
import com.davicaetano.weather.model.UnitSystem

class UnitSystemConverter {

    @TypeConverter
    fun unitToString(unitSystem: UnitSystem): String {
        return when(unitSystem) {
            Imperial -> "imperial"
            Kelvin -> "kelvin"
            Metric -> "metric"
        }
    }

    @TypeConverter
    fun stringToUnit(input: String): UnitSystem {
        return when(input) {
            "imperial" -> Imperial
            "kelvin" -> Kelvin
            else -> Metric
        }
    }

}