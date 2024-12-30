package com.davicaetano.weather.features.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davicaetano.weather.R
import com.davicaetano.weather.model.Imperial
import com.davicaetano.weather.ui.components.ForecastListItem
import com.davicaetano.weather.ui.components.HeaderItem
import com.davicaetano.weather.ui.components.SunriseAndSunsetItem
import com.davicaetano.weather.ui.components.WindItem
import com.davicaetano.weather.ui.theme.WeatherTheme
import java.math.BigDecimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    topbar: (@Composable () -> Unit) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val weatherViewState = viewModel.weatherViewState
        .collectAsStateWithLifecycle(InitialWeatherViewState).value
    val forecastViewState = viewModel.forecastViewState
        .collectAsStateWithLifecycle(InitialForecastViewState).value

    topbar.invoke {
        CenterAlignedTopAppBar(
            title = { Text(text = if (weatherViewState is SuccessWeatherViewState) {
                weatherViewState.weatherItemViewState.toolbarTitle
            } else {
                stringResource(R.string.app_name)
            }) },
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
    }

    when (weatherViewState) {
        is InitialWeatherViewState -> Text("Initial")
        is LoadingWeatherViewState -> Text("Loading...")
        is ErrorWeatherViewState -> Text("Error: ${weatherViewState.error}")
        is SuccessWeatherViewState -> {
            WeatherScreen(
                item = weatherViewState.weatherItemViewState,
                forecastViewState = forecastViewState,
                modifier = modifier
            )
        }
    }
}

@Composable
fun WeatherScreen(
    item: WeatherItemViewState,
    forecastViewState: ForecastViewState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        HeaderItem(item)
        Spacer(modifier = Modifier.height(16.dp))
        ForecastListItem(forecastViewState)
        Spacer(modifier = Modifier.height(16.dp))
        WindItem(
            wind = item.wind,
            unitSystem = item.unitSystem
        )
        Spacer(modifier = Modifier.height(16.dp))
        SunriseAndSunsetItem(
            sunrise = item.sunrise,
            sunset = item.sunset,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview(
    modifier: Modifier = Modifier
) {
    WeatherTheme {
        WeatherScreen(
            item = WeatherItemViewState(
                toolbarTitle = "Manhattan",
                title = "Mist",
                temp = "74",
                iconUrl = "https://openweathermap.org/img/wn/10d@2x.png",
                feelsLike = "74",
                high = "40",
                low = "32",
                pressure = "333",
                humidity = "100",
                visibility = "54",
                wind = WindVS(
                    speed = "12",
                    deg = BigDecimal("180"),
                ),
                clouds = "93",
                sunrise = "7:30 AM",
                sunset = "7:30 PM",
                unitSystem = Imperial,
            ),
            forecastViewState = InitialForecastViewState
        )
    }
}
