package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.DailyWeatherAdapter
import com.example.weatherapp.adapters.HourlyWeatherAdapter
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.WeatherViewModel
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.database.HourlyWeather
import com.example.weatherapp.databinding.ActivityForecastBinding

class ForecastActivity : AppCompatActivity() {
    lateinit var binding: ActivityForecastBinding
    lateinit var dailyWeatherList: ArrayList<DailyWeather>
    lateinit var hourlyWeatherList: ArrayList<HourlyWeather>
    lateinit var dailyWeatherAdapter: DailyWeatherAdapter
    lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        lateinit var vm : WeatherViewModel
        setContentView(binding.root)

        binding.forecastWetConTextView.text = intent.getStringExtra("weatherCondition")
        binding.forecastTemTextView.text = intent.getStringExtra("temp")

        val api = RetroApiInterface.create()
        val repo = WeatherRepository(api, this)

        vm = WeatherViewModel(repo)
        dailyWeatherList = ArrayList<DailyWeather>()
//        vm.updateWeather("35","139")
        vm.weather?.observe(this) {
            dailyWeatherList = it as ArrayList<DailyWeather> /* = java.util.ArrayList<com.example.weatherapp.database.DailyWeather> */
            dailyWeatherAdapter.setDailyWeather(dailyWeatherList)
        }

        hourlyWeatherList = ArrayList<HourlyWeather>()
        vm.weatherHourly?.observe(this) {
            hourlyWeatherList = it as ArrayList<HourlyWeather> /* = java.util.ArrayList<com.example.weatherapp.database.DailyWeather> */
            hourlyWeatherAdapter.setHourlyWeather(hourlyWeatherList)
        }

        var dailyRecyclerView = binding.dailyRecyclerView
        dailyWeatherAdapter = DailyWeatherAdapter(dailyWeatherList)
        dailyRecyclerView.layoutManager = LinearLayoutManager(this)
        dailyRecyclerView.adapter = dailyWeatherAdapter


        var hourlyRecyclerView = binding.hourlyRecyclerView
        hourlyWeatherAdapter = HourlyWeatherAdapter(hourlyWeatherList)
        hourlyRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        hourlyRecyclerView.adapter = hourlyWeatherAdapter
    }
}