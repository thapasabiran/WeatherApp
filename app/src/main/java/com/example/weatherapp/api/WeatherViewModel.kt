package com.example.weatherapp.api

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import com.example.weatherapp.database.CurrentWeather
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.database.JsonDbHelper
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.*
import java.util.*

class WeatherViewModel(val repo : WeatherRepository) : ViewModel() {
    lateinit var currentWeather: CurrentWeather
    var job : Job? = null
    init {
    }

    //Update the weather from API and put it into the database
    fun updateWeather(latitude : String, longitude : String) {
        /*job = CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getWeather(latitude, longitude)
            if (res.isSuccessful) {
                println(res.body())
            }
        }*/

        repo.updateWeather(latitude, longitude)
    }

    fun getDailyWeather(){
        //weather = repo.getDailyWeather()
    }

    fun getDailyWeatherObservable() : Observable<List<DailyWeather>>? {
        return repo.getDailyWeatherObservable()
    }

    //TODO: Put inside coroutine
    fun searchLocation(context: Context, location: String) : Address?  {
        var geocoder = Geocoder(context, Locale.getDefault())
        var address = geocoder.getFromLocationName(location, 3)
        return if (!address.isEmpty()) address.get(0) else null
    }

    //
    fun saveLocation(context: Context, location: String) {
        var address = searchLocation(context,location)
        CoroutineScope(Dispatchers.IO).launch {
            if (address != null) {
                var jsonString = repo.getWeather(address.latitude.toString(), address.longitude.toString())
                var saveCurrentWeather = JsonDbHelper.toCurrentWeather(jsonString.body()!!)
                var saveHourlyWeatherList = JsonDbHelper.toListHourlyWeather(jsonString.body()!!)
                var saveDailyWeatherList = JsonDbHelper.toListDailyWeather(jsonString.body()!!)
                repo.insertCurrentWeather(saveCurrentWeather)
                for (dailyWeather in saveDailyWeatherList)
                    repo.insertDailyWeather(dailyWeather)
                for (hourlyWeather in saveHourlyWeatherList)
                    repo.insertHourlyWeather(hourlyWeather)
            }
        }
    }

    fun setAsDefaultLocation(location: String) {

    }



}