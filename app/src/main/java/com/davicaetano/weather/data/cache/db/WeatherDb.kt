package com.davicaetano.weather.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.davicaetano.weather.data.cache.model.BigDecimalTypeConverter
import com.davicaetano.weather.data.cache.model.LocalDateTimeConverter
import com.davicaetano.weather.data.cache.model.LocationDbM
import com.davicaetano.weather.data.cache.model.UnitSystemConverter
import com.davicaetano.weather.data.cache.model.WeatherDbM

@Database(entities = [WeatherDbM::class, LocationDbM::class], version = 1, exportSchema = false)
@TypeConverters(
    BigDecimalTypeConverter::class,
    LocalDateTimeConverter::class,
    UnitSystemConverter::class
)
abstract class WeatherDb : RoomDatabase() {
    abstract fun userDao(): WeatherDao
}
