package com.example.weatherapp.api

import android.content.Context
import android.location.Address
import android.location.Geocoder
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.database.CurrentWeather
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.database.JsonDbHelper
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.*
import java.util.*

class WeatherViewModel(context: Context, val repo : WeatherRepository) : ViewModel() {
    var currentWeather = MutableLiveData<CurrentWeather>()
    var job : Job? = null
    lateinit var geocoder: Geocoder
    init {
        geocoder = Geocoder(context, Locale.getDefault())
    }
    fun getWeather(latitude : String, longitude : String) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = repo.getWeather(latitude,longitude)
            if(res.isSuccessful) {
                currentWeather.postValue(JsonDbHelper.toCurrentWeather(res.body()!!))
            }

        }
    }

    //Update the weather from API and put it into the database
//    fun updateWeather(latitude : String, longitude : String) {
//        /*job = CoroutineScope(Dispatchers.IO).launch {
//            var res = repo.getWeather(latitude, longitude)
//            if (res.isSuccessful) {
//                println(res.body())
//            }
//        }*/
//
//        repo.updateWeather(latitude, longitude)
//    }

    fun getDailyWeather(){
        //weather = repo.getDailyWeather()
    }

    fun getDailyWeatherObservable() : Observable<List<DailyWeather>>? {
        return repo.getDailyWeatherObservable()
    }

    //TODO: Put inside coroutine
    fun searchLocation(location: String) : Deferred<Address?>  {
        return GlobalScope.async(Dispatchers.IO) {
            var address = geocoder.getFromLocationName(location, 3)
            if (!address.isEmpty()) address.get(0) else null
        }
    }

    //Checks if the given string is a valid location
    fun isValidLocation(location: String) : Deferred<Boolean> {
        return GlobalScope.async(Dispatchers.IO) {
            var address = geocoder.getFromLocationName(location, 3)
            address.isNotEmpty()
        }
    }

    fun updateWeather(latitude: String, longitude: String) {
        repo.updateWeather(latitude,longitude)
    }
    fun setAsDefaultLocation(location: String) {

    }

}