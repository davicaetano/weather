package com.davicaetano.weather.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davicaetano.weather.R
import com.davicaetano.weather.data.ErrorSearchState
import com.davicaetano.weather.data.InitialSearchState
import com.davicaetano.weather.data.LoadingSearchState
import com.davicaetano.weather.data.SuccessSearchState
import com.davicaetano.weather.features.main.WeatherViewModel
import com.davicaetano.weather.model.Location

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: WeatherViewModel,
    onBackClick: () -> Unit,
    onSaveClick: (Location) -> Unit,
    onSearchFieldChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    topbar: (@Composable () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
) {

    val searchState = viewModel.searchState.collectAsStateWithLifecycle().value

    topbar.invoke {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(R.string.search)) },
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

    Column(
        modifier = modifier
            .padding(top = 8.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchState.searchField,
            onValueChange = { onSearchFieldChange(it) },
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .height(32.dp)
                        .clickable { onSearchClick() },
                    painter = painterResource(R.drawable.search_72dp),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClick() }
            ),
            maxLines = 1
        )
        Spacer(modifier = Modifier.size(16.dp))
        when (searchState) {
            is InitialSearchState -> {}
            is ErrorSearchState -> {
                Text("Error: ${searchState.error}")
            }

            is LoadingSearchState -> {
                Text("Loading")
            }

            is SuccessSearchState -> {
                LazyColumn {
                    items(searchState.locationList) { location ->
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.weight(1.0f),
                                text = "${location.name}, ${location.state}, ${location.country}"
                            )
                            IconButton(onClick = { onSaveClick(location) }) {
                                Icon(
                                    painter = painterResource(R.drawable.favorite_72dp),
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