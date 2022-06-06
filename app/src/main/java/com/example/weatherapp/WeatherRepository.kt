package com.example.weatherapp

class WeatherRepository(val inter : RetroApiInterface) {
    suspend fun getWeather(latitude : String, longitude : String) =
        inter.getWeather(latitude, longitude)
}