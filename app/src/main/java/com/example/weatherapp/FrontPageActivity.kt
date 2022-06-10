package com.example.weatherapp

import android.content.Context
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
    lateinit var currentWeather: CurrentWeather
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrontPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val api = RetroApiInterface.create()
        val repo = WeatherRepository(api, this)

        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)

        vm = WeatherViewModel(repo)
        currentWeather = CurrentWeather(0,0,0,0,0.0,0.0,0,0,0.0,0.0,0,0,0.0,0,0,"","","")
        vm.getCurrentWeatherSingle()?.observe(this) {
            currentWeather = it
            binding.humidityTextView.text = currentWeather.humidity.toString()
            binding.windyTextView.text = currentWeather.wind_speed.toString() + " m/s"
            binding.cloudyTextView.text = currentWeather.clouds.toString() + " %" //ToDo: Check if this is right

//            if (pref.getString("units", "K").equals("C")) {
//                var celcius = currentWeatherList[0].temp - 273.15
//                binding.tempTextView.text = celcius.toInt().toString() + " C"
//            } else if (pref.getString("units", "K").equals("F")) {
//                var fahrenheit = 1.8*(currentWeatherList[0].temp - 273.15) + 32
//                binding.tempTextView.text = fahrenheit.toInt().toString() + " F"
//            } else {
//                binding.tempTextView.text = currentWeatherList[0].temp.toInt().toString()
//            }
            binding.tempTextView.text = currentWeather.temp.toString()


            binding.cloudyText.text = currentWeather.long_description.toString()
            binding.LocationText.text = pref.getString("location", "Tokyo")
        }



        binding.forcastButton.setOnClickListener {
            var forecastIntent = Intent(this, ForecastActivity::class.java)
            forecastIntent.putExtra("location", pref.getString("location", "Tokyo"))
            forecastIntent.putExtra("temp", currentWeather.temp.toString())
//            forecastIntent.putExtra("lowTemp", currentWeatherList[0].temp)
//            forecastIntent.putExtra("highTemp", currentWeatherList[0].temp)
            forecastIntent.putExtra("weatherCondition", currentWeather.long_description)
            startActivity(forecastIntent)
        }
    }
}