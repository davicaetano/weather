package com.davicaetano.weather.data.cache.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davicaetano.weather.data.cache.model.ForecastDbM
import com.davicaetano.weather.data.cache.model.LocationDbM
import com.davicaetano.weather.data.cache.model.WeatherDbM
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather")
    fun getWeather(): Flow<List<WeatherDbM>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherDbM: WeatherDbM)

    @Delete
    suspend fun deleteWeather(weatherDbM: WeatherDbM)

    @Query("SELECT * FROM forecast")
    fun getForecast(): Flow<List<ForecastDbM>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecastDbM: ForecastDbM)

    @Query("DELETE FROM forecast WHERE lat = :lat AND lon = :lon")
    suspend fun deleteForecast(lat: Double, lon: Double)

    @Query("SELECT * FROM location")
    fun getLocation(): Flow<List<LocationDbM>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(locationDbM: LocationDbM)

    @Delete
    suspend fun deleteLocation(locationDbM: LocationDbM)

}
