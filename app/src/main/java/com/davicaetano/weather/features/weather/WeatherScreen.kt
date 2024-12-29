package com.davicaetano.weather.features.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davicaetano.weather.model.Imperial
import com.davicaetano.weather.ui.components.HeaderItem
import com.davicaetano.weather.ui.components.SunriseAndSunsetItem
import com.davicaetano.weather.ui.components.WindItem
import com.davicaetano.weather.ui.theme.WeatherTheme
import java.math.BigDecimal

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier
) {

    val viewModel: WeatherViewModel = hiltViewModel()
    val weatherViewState = viewModel.weatherViewState
        .collectAsStateWithLifecycle(InitialWeatherViewState).value

    LaunchedEffect(viewModel) {
        viewModel.fetch()
    }

    when (weatherViewState) {
        is InitialWeatherViewState -> Text("Initial")
        is LoadingWeatherViewState -> Text("Loading...")
        is ErrorWeatherViewState -> Text("Error: ${weatherViewState.error}")
        is SuccessWeatherViewState -> Weather(weatherViewState.weatherItemViewState)
    }
}

@Composable
fun Weather(
    weather: WeatherItemViewState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        HeaderItem(weather)
        Spacer(modifier = Modifier.height(16.dp))
        WindItem(
            wind = weather.wind,
            unit = weather.unit
        )
        Spacer(modifier = Modifier.height(16.dp))
        SunriseAndSunsetItem(
            sunrise = weather.sunrise,
            sunset = weather.sunset,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {

            Text(
                text = "${weather}"
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun WeatherPreview(
    modifier: Modifier = Modifier
) {
    WeatherTheme {
        Weather(
            WeatherItemViewState(
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
                unit = Imperial,
            )
        )
    }
}
