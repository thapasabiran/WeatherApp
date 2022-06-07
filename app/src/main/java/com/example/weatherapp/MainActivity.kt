package com.example.weatherapp

import android.content.Intent
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

        val intentNext = Intent(this, Map::class.java)
        startActivity(intentNext)
        this.finish()
//
//        val api = RetroApiInterface.create()
//        val repo = WeatherRepository(api, this)
//        vm = WeatherViewModel(repo)
//        vm.getWeather("35","139")
//        vm.getHourlyForecast("35","139")
    }
}