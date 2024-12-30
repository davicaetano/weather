package com.davicaetano.weather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.davicaetano.weather.R
import com.davicaetano.weather.features.weather.WeatherItemViewState
import com.davicaetano.weather.ui.theme.titleLarge2


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun HeaderItem(
    weather: WeatherItemViewState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Text(
            modifier = modifier.align(Alignment.CenterHorizontally),
            text = weather.title,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top,
        ) {
            Text(
                text = weather.temp,
                style = titleLarge2
            )
            GlideImage(
                model = weather.iconUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.feels_like, weather.feelsLike),
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier.align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.high_and_low, weather.high, weather.low),
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier.align(Alignment.CenterHorizontally),
        )
    }
}