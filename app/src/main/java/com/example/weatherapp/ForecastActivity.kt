package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.DailyWeatherAdapter
import com.example.weatherapp.adapters.HourlyWeatherAdapter
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.databinding.ActivityForecastBinding

class ForecastActivity : AppCompatActivity() {
    lateinit var binding: ActivityForecastBinding
    lateinit var dailyWeatherList: ArrayList<Main>
    lateinit var dailyWeatherAdapter: DailyWeatherAdapter
    lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dailyWeatherList = ArrayList<Main>()
        for (i in 1 .. 8) {
            var dailyWeather = Main(0.0f,0.0f,1.5f * i,1.9f * i,0.0f,0.0f)
            dailyWeatherList.add(dailyWeather)
        }

        var dailyRecyclerView = binding.dailyRecyclerView
        dailyWeatherAdapter = DailyWeatherAdapter(dailyWeatherList)
        dailyRecyclerView.layoutManager = LinearLayoutManager(this)
        dailyRecyclerView.adapter = dailyWeatherAdapter


        var hourlyRecyclerView = binding.hourlyRecyclerView
        hourlyWeatherAdapter = HourlyWeatherAdapter(dailyWeatherList)
        hourlyRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        hourlyRecyclerView.adapter = hourlyWeatherAdapter
    }
}