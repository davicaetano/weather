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
import com.davicaetano.weather.features.search.SearchScreen
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
                    onFavoriteLocationClick = { location ->
                        viewModel.fetchWeather(location)
                        viewModel.fetchForecast(location)
                        navHostController.navigate(WeatherScreenRoute(10)) {
                            launchSingleTop = true
                        }
                    },
                    onSearchClick = {
                        navHostController.navigate(SearchScreenRoute) {
                            launchSingleTop = true
                        }
                    },
                    onDeleteLocation = { location ->
                        viewModel.deleteLocation(location)
                    },
                    onLocationReturned = { location ->
                        viewModel.fetchWeather(location)
                        viewModel.fetchForecast(location)
                        navHostController.navigate(WeatherScreenRoute(10)) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable<WeatherScreenRoute>() {

                WeatherScreen(
                    viewModel = viewModel,
                    topbar = { topbar.value = it },
                    onBackClick = { navHostController.navigateUp() }
                )
            }

            composable<SearchScreenRoute>() {

                SearchScreen(
                    viewModel = viewModel,
                    onBackClick = { navHostController.navigateUp() },
                    onSaveClick = { location ->
                        navHostController.navigateUp()
                        viewModel.saveLocation(location)
                    },
                    topbar = { topbar.value = it }
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


@Serializable
data object SearchScreenRoute : Route()