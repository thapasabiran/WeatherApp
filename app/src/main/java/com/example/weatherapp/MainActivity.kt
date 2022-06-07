package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.WeatherViewModel

class MainActivity : AppCompatActivity() {
    lateinit var vm : WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//
        val api = RetroApiInterface.create()
        val repo = WeatherRepository(api, this)
        vm = WeatherViewModel(repo)
        vm.updateWeather("35","139")

        vm.weather?.observe(this, {
            item -> println("daily weather: length: ${item.size} $item")
        })

        vm.getWeather()
//        vm.getHourlyForecast("35","139")
    }
}