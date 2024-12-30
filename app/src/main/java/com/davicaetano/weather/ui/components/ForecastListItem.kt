package com.davicaetano.weather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.davicaetano.weather.R
import com.davicaetano.weather.features.weather.ErrorForecastViewState
import com.davicaetano.weather.features.weather.ForecastItemViewState
import com.davicaetano.weather.features.weather.ForecastViewState
import com.davicaetano.weather.features.weather.InitialForecastViewState
import com.davicaetano.weather.features.weather.LoadingForecastViewState
import com.davicaetano.weather.features.weather.SuccessForecastViewState
import com.davicaetano.weather.model.Imperial

@Composable
fun ForecastListItem(
    forecastViewState: ForecastViewState,
    modifier: Modifier = Modifier
) {

    when (forecastViewState) {
        is InitialForecastViewState -> Text(stringResource(R.string.initial))
        is LoadingForecastViewState -> {
            if (forecastViewState.list != null) {
                ForecastListItem(forecastViewState.list!!)
            } else {
                Text(stringResource(R.string.loading))
            }
        }

        is ErrorForecastViewState -> Text(stringResource(R.string.error,  forecastViewState.error))
        is SuccessForecastViewState -> ForecastListItem(
            forecastViewState.list
        )
    }
}

@Composable
fun ForecastListItem(
    items: List<ForecastItemViewState>,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(vertical = 16.dp)
    ) {

        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Icon(
                modifier = Modifier.height(32.dp),
                painter = painterResource(R.drawable.calendar_72dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.forecast),
                style = MaterialTheme.typography.headlineMedium,
            )
        }


        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(items) { item ->
                ForecastItem(item)
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ForecastItem(
    item: ForecastItemViewState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(80.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(end = 4.dp, top = 11.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = item.temp,
        )
        Spacer(modifier = Modifier.size(8.dp))
        GlideImage(
            model = item.iconUrl,
            contentDescription = null,
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = item.dayOfWeek,
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = item.shortHour,

            )
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastItemPreview(modifier: Modifier = Modifier) {
    ForecastItem(
        item = ForecastItemViewState(
            toolbarTitle = "",
            title = "",
            date = "",
            temp = "76",
            iconUrl = "",
            feelsLike = "",
            high = "",
            low = "",
            pressure = "",
            humidity = "",
            visibility = "",
            clouds = "",
            sunrise = "",
            sunset = "",
            unitSystem = Imperial,

            dayOfWeek = "Sun",
            shortHour = "10 PM",
        )
    )
}