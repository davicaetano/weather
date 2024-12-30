package com.davicaetano.weather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.davicaetano.weather.R
import com.davicaetano.weather.features.getRotateAngle
import com.davicaetano.weather.features.getSpeedText
import com.davicaetano.weather.features.weather.WeatherItemViewState
import com.davicaetano.weather.model.UnitSystem

@Composable
fun WindItem(
    item: WeatherItemViewState,
    unitSystem: UnitSystem,
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
                painter = painterResource(R.drawable.air_72dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Wind",
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

                Text(
                    text = "Direction",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Icon(
                    modifier = Modifier.rotate(item.getRotateAngle()),
                    painter = painterResource(R.drawable.near_me_72dp),
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Speed",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier.weight(1.0f),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = item.getSpeedText(LocalContext.current, unitSystem),
                        style = MaterialTheme.typography.displaySmall,
                    )
                }
            }
        }
    }
}