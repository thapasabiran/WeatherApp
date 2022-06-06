package com.example.weatherapp.api

import com.example.weatherapp.api.RetroApiInterface

class WeatherRepository(val inter : RetroApiInterface) {
    suspend fun getWeather(latitude : String, longitude : String) =
        inter.getWeather(latitude, longitude)
}