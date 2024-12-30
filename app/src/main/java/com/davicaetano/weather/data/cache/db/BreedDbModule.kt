package com.davicaetano.weather.data.cache.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherDbModule {

    @Singleton
    @Provides
    fun provideDb(
        @ApplicationContext context: Context
    ): WeatherDb {
        return Room.databaseBuilder(
            context,
            WeatherDb::class.java,
            "weather-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(weatherDb: WeatherDb): WeatherDao {
        return weatherDb.userDao()
    }
}
