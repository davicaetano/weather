package com.davicaetano.weather.features.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.davicaetano.weather.features.weather.WeatherScreen
import com.davicaetano.weather.ui.theme.WeatherTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,

) {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = modifier.padding(innerPadding)) {
            WeatherTheme {
                WeatherScreen()
            }
        }
    }
}