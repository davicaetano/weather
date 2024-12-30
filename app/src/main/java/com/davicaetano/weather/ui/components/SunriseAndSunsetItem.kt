package com.davicaetano.weather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.davicaetano.weather.R

@Composable
fun SunriseAndSunsetItem(
    sunrise: String,
    sunset: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Row {
            Icon(
                modifier = Modifier.height(32.dp),
                painter = painterResource(R.drawable.sunrise_sunset_72dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.sunrise_and_sunset),
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Max)
        ) {
            Column(
                modifier = Modifier.weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Icon(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.sunrise_72dp),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = sunrise,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.sunset_72dp),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = sunset,
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    }
}