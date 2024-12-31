# Take home assignment - Whop

## Davi Caetano - davicaetano@gmail.com

## App functionality
- The app uses Theme with day theme and dark theme.
- The app uses a screen flow similar to the Android Weather app: there is a Main Screen with a list of locations (Current Location and Saved Locations). If the Current Location is returned, the app goes to the Weather Screen for the Current Location with a back button that allows the user to go to the Main Screen. If no Current Location is returned, the app stays on the Main Screen so the user can see that Location permission is denied.
- From the main screen the user can go to the weather on the Current Location, any of the saved locations or go to the Search Location Screen.
- There is a 3 second delay on the API calls so we can see the cache working. The first time it will take some seconds. After that we show the cached data while the data is being fetched.

## App architecture
- The app uses the MVVM with unidirectional data flow. The repositories are responsible for the data independet of implementation. 
- Weather repository is responsible for the Current Weather and Forecast data. It caches data for the Current Location and for saved locations.
- Forecast data is cached as well but we show only Forescast for furute time. When new Forecast data is cached all the old Forecast data is removed from cache.
- When a location is removed from favorites, Current Weather and Forecast data is removed.
- The only data that is going to be in cache at any given time is: Weather and Forecast for Current Location and saved Favorites

## Tests
- There are Unit tests for the ViewModel and for the repositories.

## Apis used
- Retrofit
- Hilt
- Room
- Glide
- Turbine
- Mockk
- Google-Truth
