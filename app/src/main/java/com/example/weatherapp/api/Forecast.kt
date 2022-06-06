package com.example.weatherapp.api

import com.example.weatherapp.Coord


data class Forecast(
    var city : City
)

data class DailyWeather(
    var id :Int

)
data class WeatherDescription(
    var main : String,
    var description : String
)
data class Temperature(
    var day : Float,
    var min : Float,
    var max : Float,
    var night : Float,
    var eve : Float,
    var morn : Float
)
data class FeelsLike(
    var day : Float,
    var night : Float,
    var eve : Float,
    var morn : Float
)
data class City(
    var id : Int,
    var name : String,
    var coord : Coord,
    var country : String,
    var population : Int,
    var timezone : Int
)
