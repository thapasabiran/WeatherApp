package com.example.weatherapp

import java.util.*

data class Weather(
    var coord : Coord,
    var weath : List<Weath>,
    var base : String,
    var main : Main,
    var visibility : Int,
    var wind: Wind,
    var cloud: Cloud,
    var dt : Int,
    var sys : Sys,
    var timezone : Int,
    var id : Int,
    var name : String,
    var cod : Int
)

data class Coord(
    var lon : Int,
    var lat : Int
)
data class Weath(
    var id : Int,
    var main : String,
    var description : String,
    var icon : String
)
data class Main(
    var temp : Float,
    var feelsLike : Float,
    var tempMin : Float,
    var tempMax : Float,
    var pressure : Float,
    var humidity : Float
)
data class Wind(
    var speed : Float,
    var deg : Float,
    var gust : Float
)
data class Cloud(
    var all : String
)
data class Sys(
    var type : Int,
    var id : Int,
    var country : String,
    var sunrise : Int,
    var sunset : Int
)

