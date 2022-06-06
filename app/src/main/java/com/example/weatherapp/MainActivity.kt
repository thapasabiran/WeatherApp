package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    lateinit var vm : WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val api = RetroApiInterface.create()
        val repo = WeatherRepository(api)
        vm = WeatherViewModel(repo)
        vm.weather.observe(this) {
            println(it)
        }
        vm.getWeather("35","139")
    }
}