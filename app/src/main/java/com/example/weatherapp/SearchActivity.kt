package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.WeatherViewModel
import com.example.weatherapp.databinding.ActivityMainBinding

class SearchActivity : AppCompatActivity() {
    lateinit var repo : WeatherRepository
    lateinit var binding: ActivityMainBinding
    lateinit var vm : WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        binding = ActivityMainBinding.inflate(layoutInflater)

        repo = WeatherRepository(RetroApiInterface.create(),this)
        vm = WeatherViewModel(repo)
        vm.searchLocation(this, "London")
    }
}