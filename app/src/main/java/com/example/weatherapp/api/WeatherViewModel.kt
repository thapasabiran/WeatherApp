package com.example.weatherapp.api

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.database.CurrentWeather
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.database.HourlyWeather
import com.example.weatherapp.database.JsonDbHelper
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.*
import java.util.*

class WeatherViewModel(val repo : WeatherRepository) : ViewModel() {
    var currentWeather = MutableLiveData<CurrentWeather>()
    private var dailyWeather: LiveData<List<DailyWeather>>? = MutableLiveData()
    private var hourlyWeather: LiveData<List<HourlyWeather>>? = MutableLiveData()
    private var currentWeatherSingle: LiveData<CurrentWeather>? = MutableLiveData()
    var job : Job? = null
    fun getWeather(latitude : String, longitude : String) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getWeather(latitude,longitude)
            if(res.isSuccessful) {
                println(res.body())
                currentWeather.postValue(JsonDbHelper.toCurrentWeather(res.body()!!))
            }
        }
    }

    //ToDo: Look at this later. Dependency had been used for now to make it observable
    fun getCurrentWeatherSingle(): LiveData<CurrentWeather>? {
        viewModelScope.launch {
            currentWeatherSingle = repo.getCurrentWeatherSingle()
        }
        return currentWeatherSingle
    }

    fun getDailyWeather(): LiveData<List<DailyWeather>>? {
        viewModelScope.launch {
            dailyWeather = repo.getDailyWeather()
        }
        return dailyWeather
    }

    fun getHourlyWeather(): LiveData<List<HourlyWeather>>? {
        viewModelScope.launch {
            hourlyWeather = repo.getHourlyWeather()
        }
        return hourlyWeather
    }

    fun getDailyWeatherObservable() : Observable<List<DailyWeather>>? {
        return repo.getDailyWeatherObservable()
    }

    //TODO: Put inside coroutine
    fun searchLocation(context: Context, location: String) : Deferred<Address?>  {
        return GlobalScope.async(Dispatchers.IO) {
            var geocoder = Geocoder(context, Locale.getDefault())
            var address = geocoder.getFromLocationName(location, 3)
            if (!address.isEmpty()) address.get(0) else null
        }
    }

    //Checks if the given string is a valid location
    fun isValidLocation(context: Context, location: String) : Deferred<Boolean> {
        return GlobalScope.async(Dispatchers.IO) {
            var geocoder = Geocoder(context, Locale.getDefault())
            var address = geocoder.getFromLocationName(location, 3)
            address.isNotEmpty()
        }
    }

    suspend fun updateWeather(latitude: String, longitude: String) : Job {
        return GlobalScope.launch(Dispatchers.IO) {
            repo.updateWeather(latitude,longitude)
        }
    }

}