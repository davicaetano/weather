package com.davicaetano.weather

import app.cash.turbine.test
import com.davicaetano.weather.data.ErrorForecastState
import com.davicaetano.weather.data.ErrorSearchState
import com.davicaetano.weather.data.ErrorWeatherState
import com.davicaetano.weather.data.InitialForecastState
import com.davicaetano.weather.data.InitialSearchState
import com.davicaetano.weather.data.InitialWeatherState
import com.davicaetano.weather.data.LoadingForecastState
import com.davicaetano.weather.data.LoadingSearchState
import com.davicaetano.weather.data.LoadingWeatherState
import com.davicaetano.weather.data.SuccessForecastState
import com.davicaetano.weather.data.SuccessSearchState
import com.davicaetano.weather.data.SuccessWeatherState
import com.davicaetano.weather.data.WeatherRepository
import com.davicaetano.weather.data.cache.WeatherCache
import com.davicaetano.weather.data.network.model.ForecastDataResult
import com.davicaetano.weather.data.network.model.WeatherDataResult
import com.davicaetano.weather.data.network.service.WeatherApiService
import com.davicaetano.weather.model.Coord
import com.davicaetano.weather.model.Forecast
import com.davicaetano.weather.model.Imperial
import com.davicaetano.weather.model.Weather
import com.davicaetano.weather.model.getForecast
import com.davicaetano.weather.model.getWeather
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
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
import java.time.LocalDateTime

class WeatherRepositoryTest {

    @MockK
    lateinit var weatherApiService: WeatherApiService

    @MockK
    lateinit var weatherCache: WeatherCache

    lateinit var weatherRepository: WeatherRepository

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
    fun `test weather api Happy Path`() {
        testScope.runTest {

            basicSetup()

            weatherRepository = WeatherRepository(
                weatherApiService,
                weatherCache
            )

            weatherRepository.weatherState.test {
                assertThat(awaitItem()).isInstanceOf(InitialWeatherState::class.java)
                weatherRepository.fetchWeather(Coord(), Imperial, true)
                assertThat(awaitItem()).isInstanceOf(LoadingWeatherState::class.java)
                assertThat(awaitItem()).isInstanceOf(SuccessWeatherState::class.java)
            }
        }
    }

    @Test
    fun `test weather api error without cache`() {
        testScope.runTest {

            basicSetup()

            coEvery { weatherCache.getWeather() } returns MutableStateFlow(
                listOf<Weather>()
            ).asStateFlow()

            coEvery {
                weatherApiService.getWeatherData(any(), any(), any(), any())
            } returns retrofit2.Response.error(
                404,
                "".toResponseBody("application/json; charset=utf-8".toMediaType())
            )

            weatherRepository = WeatherRepository(
                weatherApiService,
                weatherCache
            )

            weatherRepository.weatherState.test {
                assertThat(awaitItem()).isInstanceOf(InitialWeatherState::class.java)
                weatherRepository.fetchWeather(Coord(), Imperial, true)
                assertThat(awaitItem()).isInstanceOf(LoadingWeatherState::class.java)
                val errorItem = awaitItem()
                assertThat(errorItem).isInstanceOf(ErrorWeatherState::class.java)
                assertThat(errorItem.weather).isNull()
            }
        }
    }

    @Test
    fun `test weather api error with cache`() {
        testScope.runTest {

            basicSetup()

            coEvery {
                weatherApiService.getWeatherData(any(), any(), any(), any())
            } returns retrofit2.Response.error(
                404,
                "".toResponseBody("application/json; charset=utf-8".toMediaType())
            )

            weatherRepository = WeatherRepository(
                weatherApiService,
                weatherCache
            )

            weatherRepository.weatherState.test {
                assertThat(awaitItem()).isInstanceOf(InitialWeatherState::class.java)
                weatherRepository.fetchWeather(Coord(), Imperial, true)
                assertThat(awaitItem()).isInstanceOf(LoadingWeatherState::class.java)
                val errorItem = awaitItem()
                assertThat(errorItem).isInstanceOf(ErrorWeatherState::class.java)
                assertThat(errorItem.weather).isNotNull()
            }
        }
    }

    @Test
    fun `test forecast api Happy Path`() {
        testScope.runTest {

            basicSetup()

            weatherRepository = WeatherRepository(
                weatherApiService,
                weatherCache
            )

            weatherRepository.forecastState.test {
                assertThat(awaitItem()).isInstanceOf(InitialForecastState::class.java)
                weatherRepository.fetchForecast(Coord(), Imperial, true)
                assertThat(awaitItem()).isInstanceOf(LoadingForecastState::class.java)
                assertThat(awaitItem()).isInstanceOf(SuccessForecastState::class.java)
            }
        }
    }

    @Test
    fun `test forecast api error without cache`() {
        testScope.runTest {

            basicSetup()

            coEvery {
                weatherApiService.getForecastData(any(), any(), any(), any())
            } returns retrofit2.Response.error(
                404,
                "".toResponseBody("application/json; charset=utf-8".toMediaType())
            )

            coEvery { weatherCache.getForecast() } returns MutableStateFlow(
                listOf<Forecast>()
            ).asStateFlow()

            weatherRepository = WeatherRepository(
                weatherApiService,
                weatherCache
            )

            weatherRepository.forecastState.test {
                assertThat(awaitItem()).isInstanceOf(InitialForecastState::class.java)
                weatherRepository.fetchForecast(Coord(), Imperial, true)
                assertThat(awaitItem()).isInstanceOf(LoadingForecastState::class.java)
                val errorItem = awaitItem()
                assertThat(errorItem).isInstanceOf(ErrorForecastState::class.java)
                assertThat(errorItem.forecast).isEmpty()
            }
        }
    }

    @Test
    fun `test forecast api error with cache`() {
        testScope.runTest {

            basicSetup()

            coEvery {
                weatherApiService.getForecastData(any(), any(), any(), any())
            } returns retrofit2.Response.error(
                404,
                "".toResponseBody("application/json; charset=utf-8".toMediaType())
            )

            weatherRepository = WeatherRepository(
                weatherApiService,
                weatherCache
            )

            weatherRepository.forecastState.test {
                assertThat(awaitItem()).isInstanceOf(InitialForecastState::class.java)
                weatherRepository.fetchForecast(Coord(), Imperial, true)
                assertThat(awaitItem()).isInstanceOf(LoadingForecastState::class.java)
                val errorItem = awaitItem()
                assertThat(errorItem).isInstanceOf(ErrorForecastState::class.java)
                assertThat(errorItem.forecast).isNotEmpty()
            }
        }
    }

    @Test
    fun `test search api Happy Path`() {
        testScope.runTest {

            basicSetup()

            weatherRepository = WeatherRepository(
                weatherApiService,
                weatherCache
            )

            weatherRepository.searchState.test {
                assertThat(awaitItem()).isInstanceOf(InitialSearchState::class.java)
                weatherRepository.onSearchChange("Manhattan")
                assertThat(awaitItem()).isInstanceOf(InitialSearchState::class.java)
                weatherRepository.fetchSearch()
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

            weatherRepository = WeatherRepository(
                weatherApiService,
                weatherCache
            )

            weatherRepository.searchState.test {
                assertThat(awaitItem()).isInstanceOf(InitialSearchState::class.java)
                weatherRepository.onSearchChange("Manhattan")
                assertThat(awaitItem()).isInstanceOf(InitialSearchState::class.java)
                weatherRepository.fetchSearch()
                assertThat(awaitItem()).isInstanceOf(LoadingSearchState::class.java)
                assertThat(awaitItem()).isInstanceOf(ErrorSearchState::class.java)
            }
        }
    }

    private fun basicSetup() {

        coEvery {
            weatherApiService.getWeatherData(any(), any(), any(), any())
        } returns retrofit2.Response.success(200, WeatherDataResult())

        coEvery { weatherCache.getWeather() } returns MutableStateFlow(
            listOf(getWeather())
        ).asStateFlow()

        coEvery { weatherCache.saveWeather(any(), any()) } just runs

        coEvery {
            weatherApiService.getForecastData(any(), any(), any(), any())
        } returns retrofit2.Response.success(200, ForecastDataResult())

        coEvery { weatherCache.getForecast() } returns MutableStateFlow(
            listOf(getForecast().copy(date = LocalDateTime.now().plusDays(1)))
        ).asStateFlow()

        coEvery { weatherCache.saveForecast(any(), any()) } just runs
        coEvery { weatherCache.saveForecastList(any(), any()) } just runs

        coEvery {
            weatherApiService.getLocationData(any(), any(), any())
        } returns retrofit2.Response.success(200, listOf())
    }
}