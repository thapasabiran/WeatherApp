package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.DailyWeatherAdapter
import com.example.weatherapp.adapters.HourlyWeatherAdapter
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
        setContentView(binding.root)

        dailyWeatherList = ArrayList<DailyWeather>()
        for (i in 1 .. 8) {
            var dailyWeather = DailyWeather(null,0, 0, 0, 0, 0, 0.0, 1.5 * i ,2.5 * i, 0.0, 0.0, 0.0, 0.0, 0, 0,0.0,0.0,0,0.0,0,"","","",0,0.0,0.0,0.0,0.0)
            dailyWeatherList.add(dailyWeather)
        }

        hourlyWeatherList = ArrayList<HourlyWeather>()
        for (i in 1 .. 8) {
            var hourlyWeather = HourlyWeather(null,1654186183, 2.5 * i, 0.0, 0, 0, 0.0, 0.0 ,0, 0, 0.0, 0, 0.0, 0, "","","",0.0)
            hourlyWeatherList.add(hourlyWeather)
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