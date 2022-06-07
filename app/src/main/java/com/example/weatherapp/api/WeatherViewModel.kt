package com.example.weatherapp.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject

class WeatherViewModel(val repo : WeatherRepository) : ViewModel() {
    var weather = MutableLiveData<JSONObject>()
    var job : Job? = null

    //takes latitude and longitude of the location you want the weather data
    fun getWeather(latitude : String, longitude : String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getWeather(latitude, longitude)
            if (res.isSuccessful) {
                println(res.body())
            }
        }
    }



}