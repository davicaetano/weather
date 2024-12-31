package com.davicaetano.weather

import app.cash.turbine.test
import com.davicaetano.weather.data.ErrorSearchState
import com.davicaetano.weather.data.InitialSearchState
import com.davicaetano.weather.data.LoadingSearchState
import com.davicaetano.weather.data.SuccessSearchState
import com.davicaetano.weather.data.cache.WeatherCache
import com.davicaetano.weather.data.location.InitialLocationState
import com.davicaetano.weather.data.location.LocationRepository
import com.davicaetano.weather.data.location.SuccessLocationState
import com.davicaetano.weather.data.network.service.WeatherApiService
import com.davicaetano.weather.model.Coord
import com.davicaetano.weather.model.Location
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test

class LocationRepositoryTest {

    @MockK
    lateinit var weatherApiService: WeatherApiService

    @MockK
    lateinit var weatherCache: WeatherCache

    lateinit var locationRepository: LocationRepository

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
    fun `test search api Happy Path`() {
        testScope.runTest {

            basicSetup()

            locationRepository = LocationRepository(
                weatherApiService,
                weatherCache
            )

            locationRepository.searchState.test {
                assertThat(awaitItem()).isInstanceOf(InitialSearchState::class.java)
                locationRepository.onSearchChange("Manhattan")
                assertThat(awaitItem()).isInstanceOf(InitialSearchState::class.java)
                locationRepository.fetchSearch()
                assertThat(awaitItem()).isInstanceOf(LoadingSearchState::class.java)
                assertThat(awaitItem()).isInstanceOf(SuccessSearchState::class.java)
            }
        }
    }

    @Test
    fun `test search api error`() {
        testScope.runTest {

            basicSetup()

            coEvery {
                weatherApiService.getLocationData(any(), any(), any())
            } returns retrofit2.Response.error(
                404,
                "".toResponseBody("application/json; charset=utf-8".toMediaType())
            )

            locationRepository = LocationRepository(
                weatherApiService,
                weatherCache
            )

            locationRepository.searchState.test {
                assertThat(awaitItem()).isInstanceOf(InitialSearchState::class.java)
                locationRepository.onSearchChange("Manhattan")
                assertThat(awaitItem()).isInstanceOf(InitialSearchState::class.java)
                locationRepository.fetchSearch()
                assertThat(awaitItem()).isInstanceOf(LoadingSearchState::class.java)
                assertThat(awaitItem()).isInstanceOf(ErrorSearchState::class.java)
            }
        }
    }


    @Test
    fun `test location`() {
        testScope.runTest {

            basicSetup()

            locationRepository = LocationRepository(
                weatherApiService,
                weatherCache
            )

            locationRepository.locationState.test {
                assertThat(awaitItem()).isInstanceOf(InitialLocationState::class.java)
                locationRepository.setLocation(SuccessLocationState(Coord()))
                assertThat(awaitItem()).isInstanceOf(SuccessLocationState::class.java)
            }
        }
    }

    private fun basicSetup() {

        coEvery {
            weatherApiService.getLocationData(any(), any(), any())
        } returns retrofit2.Response.success(200, listOf())

        coEvery { weatherCache.getLocations() } returns MutableStateFlow(
            listOf(Location())
        ).asStateFlow()
    }
}