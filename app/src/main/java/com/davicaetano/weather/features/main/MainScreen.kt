package com.davicaetano.weather.features.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.davicaetano.weather.features.locationlist.LocationListScreen
import com.davicaetano.weather.features.weather.WeatherScreen
import com.davicaetano.weather.features.weather.WeatherViewModel
import kotlinx.serialization.Serializable

@Composable
fun MainScreen(
    navHostController: NavHostController = rememberNavController(),
    onRequestLocationClick: () -> Unit,
    modifier: Modifier = Modifier,

    ) {

    val viewModel: WeatherViewModel = hiltViewModel()
    val topbar = remember { mutableStateOf<@Composable () -> Unit>({}) }



    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = topbar.value
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = LocationListRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<LocationListRoute>() {
                LocationListScreen(
                    viewModel = viewModel,
                    topbar = { topbar.value = it },
                    onCurrentLocationClick = {
                        onRequestLocationClick()
                    },
                    onLocationReturned = {
                        viewModel.fetchWeather()
                        navHostController.navigate(WeatherScreenRoute(10)){
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable<WeatherScreenRoute>() {
//                val weatherScreenRoute: WeatherScreenRoute = it.toRoute()

                WeatherScreen(
                    viewModel = viewModel,
                    topbar = { topbar.value = it },
                    onBackClick = { navHostController.navigateUp() }
                )
            }
        }
    }
}

sealed class Route

@Serializable
data object LocationListRoute : Route()

@Serializable
data class WeatherScreenRoute(val id: Int) : Route()
