package com.davicaetano.weather

import app.cash.turbine.test
import com.davicaetano.weather.data.ErrorForecastState
import com.davicaetano.weather.data.ErrorSearchState
import com.davicaetano.weather.data.ErrorWeatherState
import com.davicaetano.weather.data.SuccessForecastState
import com.davicaetano.weather.data.SuccessSearchState
import com.davicaetano.weather.data.SuccessWeatherState
import com.davicaetano.weather.data.WeatherRepository
import com.davicaetano.weather.data.location.LocationRepository
import com.davicaetano.weather.data.location.SuccessLocationState
import com.davicaetano.weather.data.unit.UnitRepository
import com.davicaetano.weather.features.main.WeatherViewModel
import com.davicaetano.weather.features.weather.ErrorForecastViewState
import com.davicaetano.weather.features.weather.ErrorWeatherViewState
import com.davicaetano.weather.features.weather.SuccessForecastViewState
import com.davicaetano.weather.features.weather.SuccessWeatherViewState
import com.davicaetano.weather.model.Coord
import com.davicaetano.weather.model.Location
import com.davicaetano.weather.model.getWeather
import com.davicaetano.weather.ui.WeatherFormatter
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {

    @MockK
    lateinit var weatherRepository: WeatherRepository

    @MockK
    lateinit var weatherFormatter: WeatherFormatter

    @MockK
    lateinit var locationRepository: LocationRepository

    @MockK
    lateinit var unitRepository: UnitRepository

    lateinit var weatherViewModel: WeatherViewModel

    private lateinit var testScheduler: TestCoroutineScheduler
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope

    @Before
    fun setup() {
        testScheduler = TestCoroutineScheduler()
        testDispatcher = StandardTestDispatcher(testScheduler)
        testScope = TestScope(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `test Happy Path`() {
        testScope.runTest {

            basicSetup()

            weatherViewModel = WeatherViewModel(
                weatherRepository,
                weatherFormatter,
                locationRepository,
                unitRepository
            )

            weatherViewModel.weatherViewState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessWeatherViewState::class.java)
            }
            weatherViewModel.forecastViewState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessForecastViewState::class.java)
            }
            weatherViewModel.searchState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessSearchState::class.java)
            }
            weatherViewModel.locationState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessLocationState::class.java)
            }
            weatherViewModel.favoriteState.test {
                assertThat(awaitItem()).isNotEmpty()
            }
        }
    }

    @Test
    fun `test Weather Repository with Error`() {
        testScope.runTest {

            basicSetup()

            coEvery { weatherRepository.weatherState } returns MutableStateFlow(
                ErrorWeatherState(getWeather(), Throwable())
            ).asStateFlow()

            weatherViewModel = WeatherViewModel(
                weatherRepository,
                weatherFormatter,
                locationRepository,
                unitRepository
            )

            weatherViewModel.weatherViewState.test {
                assertThat(awaitItem()).isInstanceOf(ErrorWeatherViewState::class.java)
            }
            weatherViewModel.forecastViewState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessForecastViewState::class.java)
            }
            weatherViewModel.searchState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessSearchState::class.java)
            }
            weatherViewModel.locationState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessLocationState::class.java)
            }
            weatherViewModel.favoriteState.test {
                assertThat(awaitItem()).isNotEmpty()
            }
        }
    }

    @Test
    fun `test Forecast Repository with Error`() {
        testScope.runTest {

            basicSetup()

            coEvery { weatherRepository.forecastState } returns MutableStateFlow(
                ErrorForecastState(listOf(), Throwable())
            ).asStateFlow()

            weatherViewModel = WeatherViewModel(
                weatherRepository,
                weatherFormatter,
                locationRepository,
                unitRepository
            )

            weatherViewModel.weatherViewState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessWeatherViewState::class.java)
            }
            weatherViewModel.forecastViewState.test {
                assertThat(awaitItem()).isInstanceOf(ErrorForecastViewState::class.java)
            }
            weatherViewModel.searchState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessSearchState::class.java)
            }
            weatherViewModel.locationState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessLocationState::class.java)
            }
            weatherViewModel.favoriteState.test {
                assertThat(awaitItem()).isNotEmpty()
            }
        }
    }


    @Test
    fun `test Search Repository Error`() {
        testScope.runTest {

            basicSetup()

            coEvery { weatherRepository.searchState } returns MutableStateFlow(
                ErrorSearchState(
                    searchField = "",
                    error = Throwable()
                )
            ).asStateFlow()

            every { weatherFormatter.getFormattedBigDecimal(any()) } returns ""
            every { weatherFormatter.getTime(any()) } returns ""
            every { weatherFormatter.getShortHour(any()) } returns ""
            every { weatherFormatter.getShortDayOfTheWeek(any()) } returns ""


            weatherViewModel = WeatherViewModel(
                weatherRepository,
                weatherFormatter,
                locationRepository,
                unitRepository
            )

            weatherViewModel.weatherViewState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessWeatherViewState::class.java)
            }
            weatherViewModel.forecastViewState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessForecastViewState::class.java)
            }
            weatherViewModel.searchState.test {
                assertThat(awaitItem()).isInstanceOf(ErrorSearchState::class.java)
            }
            weatherViewModel.locationState.test {
                assertThat(awaitItem()).isInstanceOf(SuccessLocationState::class.java)
            }
            weatherViewModel.favoriteState.test {
                assertThat(awaitItem()).isNotEmpty()
            }
        }
    }

    private fun basicSetup() {

        coEvery { locationRepository.locationState } returns MutableStateFlow(
            SuccessLocationState(Coord(1.0, 2.0))
        ).asStateFlow()

        coEvery { locationRepository.favoriteState } returns MutableStateFlow(
            listOf(Location())
        ).asStateFlow()

        coEvery { weatherRepository.searchState } returns MutableStateFlow(
            SuccessSearchState(
                searchField = "",
                locationList = listOf()
            )
        ).asStateFlow()

        coEvery { weatherRepository.weatherState } returns MutableStateFlow(
            SuccessWeatherState(getWeather())
        ).asStateFlow()

        coEvery { weatherRepository.forecastState } returns MutableStateFlow(
            SuccessForecastState(listOf())
        ).asStateFlow()

        every { weatherFormatter.getFormattedBigDecimal(any()) } returns ""
        every { weatherFormatter.getTime(any()) } returns ""
        every { weatherFormatter.getShortHour(any()) } returns ""
        every { weatherFormatter.getShortDayOfTheWeek(any()) } returns ""
    }
}