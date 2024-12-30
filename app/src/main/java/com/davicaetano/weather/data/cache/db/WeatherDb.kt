package com.davicaetano.weather.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.davicaetano.weather.data.cache.model.ForecastDbM
import com.davicaetano.weather.data.cache.model.converters.BigDecimalTypeConverter
import com.davicaetano.weather.data.cache.model.converters.LocalDateTimeConverter
import com.davicaetano.weather.data.cache.model.LocationDbM
import com.davicaetano.weather.data.cache.model.converters.UnitSystemConverter
import com.davicaetano.weather.data.cache.model.WeatherDbM

@Database(
    entities = [WeatherDbM::class, ForecastDbM::class, LocationDbM::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    BigDecimalTypeConverter::class,
    LocalDateTimeConverter::class,
    UnitSystemConverter::class
)
abstract class WeatherDb : RoomDatabase() {
    abstract fun userDao(): WeatherDao
}
