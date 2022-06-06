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


        val api = RetroApiInterface.create()
        val repo = WeatherRepository(api, this)
        vm = WeatherViewModel(repo)
        vm.weather.observe(this) {
            println(it)
        }
        vm.getWeather("35","139")
    }
}