package com.davicaetano.weather.features.locationlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davicaetano.weather.R
import com.davicaetano.weather.features.weather.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListScreen(
    viewModel: WeatherViewModel,
    topbar: (@Composable () -> Unit) -> Unit,
    onCurrentLocationClick: () -> Unit,
    onLocationReturned: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val state = rememberSaveable { mutableStateOf(0) }

    val location = viewModel.locationState.collectAsStateWithLifecycle().value
    if (location != null && state.value == 0) {
        state.value = 1
        onLocationReturned()
    }

    topbar.invoke {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
        )
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Row(modifier = Modifier
            .clickable {
                state.value = 0
                onCurrentLocationClick()
            }
        ) {
            Icon(
                modifier = Modifier.height(32.dp),
                painter = painterResource(R.drawable.location_on_72dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "Current Location",
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
    }
}