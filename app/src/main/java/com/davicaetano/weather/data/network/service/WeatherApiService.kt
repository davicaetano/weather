package com.davicaetano.weather.data.network.service

import com.davicaetano.weather.data.network.model.WeatherDataResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "64f76562606abb270a19ac903b442fdc"

interface WeatherApiService {

    @GET("data/2.5/forecast")
    suspend fun getWeatherData(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("appid") apiKey: String = API_KEY,
    ): Response<WeatherDataResult>
}