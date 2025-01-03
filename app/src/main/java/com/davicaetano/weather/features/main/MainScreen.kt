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
import kotlinx.serialization.Serializable

@Composable
fun MainScreen(
    navHostController: NavHostController = rememberNavController(),
    onRequestLocationClick: () -> Unit,
    modifier: Modifier = Modifier,

    ) {

    val viewModel: WeatherViewModel = hiltViewModel()
    val topbar = remember { mutableStateOf<@Composable () -> Unit>({}) }
    val fab = remember { mutableStateOf<@Composable () -> Unit>({}) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = topbar.value,
        floatingActionButton = fab.value,
    ) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = LocationListRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<LocationListRoute>() {
                LocationListScreen(
                    viewModel = viewModel,
                    onCurrentLocationClick = {
                        onRequestLocationClick()
                    },
                    onFavoriteLocationClick = { location ->
                        viewModel.fetchWeather(location, false)
                        viewModel.fetchForecast(location, false)
                        navHostController.navigate(WeatherScreenRoute) {
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
                        viewModel.fetchWeather(location, true)
                        viewModel.fetchForecast(location, true)
                        navHostController.navigate(WeatherScreenRoute) {
                            launchSingleTop = true
                        }
                    },
                    topbar = { topbar.value = it },
                    fab = { fab.value = it }
                )
            }

            composable<WeatherScreenRoute>() {
                fab.value = {}
                WeatherScreen(
                    viewModel = viewModel,
                    topbar = { topbar.value = it },
                    onBackClick = {
                        viewModel.cancelJobs()
                        navHostController.navigateUp()
                    }
                )
            }

            composable<SearchScreenRoute>() {
                fab.value = {}
                SearchScreen(
                    viewModel = viewModel,
                    onBackClick = {
                        viewModel.cancelJobs()
                        navHostController.navigateUp()
                    },
                    onSaveClick = { location ->
                        navHostController.navigateUp()
                        viewModel.saveLocation(location)
                    },
                    onSearchFieldChange = { viewModel.onSearchChange(it) },
                    onSearchClick = { viewModel.onSearchClick() },
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
data object WeatherScreenRoute : Route()

@Serializable
data object SearchScreenRoute : Route()