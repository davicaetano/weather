package com.davicaetano.weather.data.network.service

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkComponent {

    @Singleton
    @Provides
    fun provideWeatherApiService(settings: NetworkServiceSettings): WeatherApiService {
        return settings.weatherApiService
    }
}