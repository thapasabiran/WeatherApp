package com.example.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherViewModel(val repo : WeatherRepository) : ViewModel() {
    var weather = MutableLiveData<Weather>()
    var job : Job? = null

    //takes latitude and longitude of the location you want the weather
    //data for and returns a weather data object
    fun getWeather(latitude : String, longitude : String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getWeather(latitude, longitude)
            if (res.isSuccessful) {
                weather.postValue(res.body())
            }
        }
    }


}