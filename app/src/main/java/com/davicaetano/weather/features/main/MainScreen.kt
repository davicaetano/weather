package com.davicaetano.weather.features.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davicaetano.weather.data.ErrorWeatherResult
import com.davicaetano.weather.data.InitialWeatherResult
import com.davicaetano.weather.data.LoadingWeatherResult
import com.davicaetano.weather.data.SuccessWeatherResult

@Composable
fun MainScreen(
    modifier: Modifier = Modifier
) {

    val viewModel: MainScreenViewModel = hiltViewModel()
    val data = viewModel.data.collectAsStateWithLifecycle().value

    LaunchedEffect(viewModel) {
        viewModel.fetch()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Box(modifier = modifier.padding(innerPadding)) {
            when (data) {
                is InitialWeatherResult -> Text("Initial")
                is LoadingWeatherResult -> Text("Loading...")
                is ErrorWeatherResult -> Text("Error: ${data.error}")
                is SuccessWeatherResult ->
                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                        Text("Success: ${data.result}")
                    }
            }
        }
    }
}