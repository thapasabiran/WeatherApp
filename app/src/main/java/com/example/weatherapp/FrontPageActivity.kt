package com.example.weatherapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.WeatherViewModel
import com.example.weatherapp.database.CurrentWeather
import com.example.weatherapp.databinding.ActivityFrontPageBinding

class FrontPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityFrontPageBinding
    lateinit var vm : WeatherViewModel
    lateinit var currentWeather: CurrentWeather
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrontPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val api = RetroApiInterface.create()
        val repo = WeatherRepository(api, this)
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)

        vm = WeatherViewModel(repo)
        //create current weather object
        currentWeather = CurrentWeather(0,0,0,0,0.0,0.0,0,0,0.0,0.0,0,0,0.0,0,0,"","","")
        //Get current weather (single not list) and bind it to recycler view adapter
        vm.getCurrentWeatherSingle()?.observe(this) {
            currentWeather = it
            binding.humidityTextView.text = currentWeather.humidity.toString()
            binding.windyTextView.text = currentWeather.wind_speed.toString() + " m/s"
            binding.cloudyTextView.text = currentWeather.clouds.toString() + " %"
            binding.tempTextView.text = currentWeather.temp.toString()
            binding.cloudyText.text = currentWeather.long_description.toString()
            binding.LocationText.text = pref.getString("location", "") //get the saved location from preference
        }

        binding.forcastButton.setOnClickListener {
            var forecastIntent = Intent(this, ForecastActivity::class.java)
            forecastIntent.putExtra("location", pref.getString("location", ""))
            forecastIntent.putExtra("temp", currentWeather.temp.toString())
            forecastIntent.putExtra("weatherCondition", currentWeather.long_description)
            startActivity(forecastIntent)
        }

        binding.weatAlertButton.setOnClickListener {
            var alertIntent = Intent(this, WarningActivity::class.java)
            startActivity(alertIntent)
        }
    }
}