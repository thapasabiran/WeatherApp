package com.example.weatherapp.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.database.DailyWeather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject

class WeatherViewModel(val repo : WeatherRepository) : ViewModel() {
    var weather : LiveData<List<DailyWeather>>?
    var job : Job? = null
    init {
        weather = repo.getDailyWeather()
    }

    //takes latitude and longitude of the location you want the weather data
    fun updateWeather(latitude : String, longitude : String) {
        /*job = CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getWeather(latitude, longitude)
            if (res.isSuccessful) {
                println(res.body())
            }
        }*/

        repo.updateWeather(latitude, longitude)
    }

    fun getWeather(){
        weather = repo.getDailyWeather()
    }

    fun getHourlyForecast(latitude : String, longitude : String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getHourlyForecast(latitude, longitude)
            if (res.isSuccessful) {
                println(res.body())
            }
        }
    }

    fun getDailyForecast(latitude : String, longitude : String) {
        job = CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getDailyForecast(latitude, longitude)
            if (res.isSuccessful) {
                println(res.body())
            }
        }
    }


}