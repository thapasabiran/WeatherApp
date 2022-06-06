package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.WeatherViewModel
import com.example.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var vm : WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.welcomeContinueButton.setOnClickListener {
            val frontPageIntent = Intent(this, FrontPageActivity::class.java)
            startActivity(frontPageIntent)
        }

//        val api = RetroApiInterface.create()
//        val repo = WeatherRepository(api)
//        vm = WeatherViewModel(repo)
//        vm.weather.observe(this) {
//            println(it)
//        }
//        vm.getWeather("35","139")
    }
}