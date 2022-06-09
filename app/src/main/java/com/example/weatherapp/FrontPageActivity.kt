package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.adapters.DailyWeatherAdapter
import com.example.weatherapp.adapters.HourlyWeatherAdapter
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.WeatherViewModel
import com.example.weatherapp.database.CurrentWeather
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.database.HourlyWeather
import com.example.weatherapp.databinding.ActivityForecastBinding
import com.example.weatherapp.databinding.ActivityFrontPageBinding

class FrontPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityFrontPageBinding
    lateinit var vm : WeatherViewModel
    lateinit var currentWeatherList: ArrayList<CurrentWeather>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrontPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val api = RetroApiInterface.create()
        val repo = WeatherRepository(api, this)

        vm = WeatherViewModel(repo)
        currentWeatherList = ArrayList<CurrentWeather>()
        vm.weatherCurrent?.observe(this) {
            currentWeatherList = it as ArrayList<CurrentWeather> /* = java.util.ArrayList<com.example.weatherapp.database.DailyWeather> */
            binding.humidityTextView.text = currentWeatherList[0].humidity.toString()
            binding.windyTextView.setText(currentWeatherList[0].wind_speed.toString() + " m/s")
            binding.cloudyTextView.text = currentWeatherList[0].clouds.toString() + " %"
            binding.tempTextView.text = currentWeatherList[0].temp.toString() + " C"
            binding.weatherDescriptionTextView.text = currentWeatherList[0].long_description.toString()
//            binding.locationTextView.text = currentWeatherList[0].long_description.toString()
        }



        binding.forcastButton.setOnClickListener {
            var forecastIntent = Intent(this, ForecastActivity::class.java)
//            forecastIntent.putExtra("location", currentWeatherList[0].long_description)
            forecastIntent.putExtra("temp", currentWeatherList[0].temp)
//            forecastIntent.putExtra("lowTemp", currentWeatherList[0].temp)
//            forecastIntent.putExtra("highTemp", currentWeatherList[0].temp)
            forecastIntent.putExtra("weatherCondition", currentWeatherList[0].long_description)
            startActivity(forecastIntent)
        }
    }
}