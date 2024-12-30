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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davicaetano.weather.R
import com.davicaetano.weather.data.InitialSearchState
import com.davicaetano.weather.data.location.DeniedLocationState
import com.davicaetano.weather.data.location.InitialLocationState
import com.davicaetano.weather.data.location.LoadingLocationState
import com.davicaetano.weather.data.location.LocationState
import com.davicaetano.weather.data.location.SuccessLocationState
import com.davicaetano.weather.features.weather.WeatherViewModel
import com.davicaetano.weather.model.Location
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListScreen(
    viewModel: WeatherViewModel,
    onCurrentLocationClick: () -> Unit,
    onFavoriteLocationClick: (Location) -> Unit,
    onSearchClick: () -> Unit,
    onDeleteLocation: (Location) -> Unit,
    onLocationReturned: (Location) -> Unit,
    topbar: (@Composable () -> Unit) -> Unit,
    fab: (@Composable () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
) {

    val state = rememberSaveable { mutableStateOf(0) }

    val locationList = viewModel.favoriteState
        .collectAsStateWithLifecycle(listOf()).value

    val locationState = viewModel.locationState.onEach { location ->
        if (location is SuccessLocationState && state.value == 0) {
            state.value = 1
            onLocationReturned(Location(lat = location.coord.lat, lon = location.coord.lon))
        }
    }.collectAsStateWithLifecycle(InitialLocationState()).value


    topbar.invoke {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
        )
    }
    fab.invoke {
        FloatingActionButton(
            onClick = { onSearchClick() }
        ) {
            Icon(
                painter = painterResource(R.drawable.favorite_72dp),
                contentDescription = "Save"
            )
        }
    }

    Column {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    state.value = 0
                    onCurrentLocationClick()
                }
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier

            ) {
                Icon(
                    modifier = Modifier.height(32.dp),
                    painter = painterResource(R.drawable.location_on_72dp),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    text = when (locationState) {
                        is DeniedLocationState -> "Location Denied"
                        is InitialLocationState -> "Initial"
                        is LoadingLocationState -> "Loading location"
                        is SuccessLocationState -> "Current Location"
                    },
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
        }

        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.favorites),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            if (locationList.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_favorites),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(vertical = 16.dp),
                )
            } else {
                LazyColumn {
                    items(locationList) { location ->
                        Row(
                            modifier = Modifier
                                .clickable { onFavoriteLocationClick(location) }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1.0f),
                                text = "${location.name}, ${location.state}, ${location.country}"
                            )
                            IconButton(onClick = { onDeleteLocation(location) }) {
                                Icon(
                                    painter = painterResource(R.drawable.delete_72dp),
                                    contentDescription = "Save"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}