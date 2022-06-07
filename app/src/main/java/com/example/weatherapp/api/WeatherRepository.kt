package com.example.weatherapp.api

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherRepository(val inter : RetroApiInterface, context: Context) {

    val db : WeatherDao? = AppDatabase.getInstance(context)?.weatherDao()

    //retrofit part - used to update the database, don't use in viewmodel
    suspend fun getWeather(latitude : String, longitude : String) =
        inter.getWeather(latitude, longitude)
    suspend fun getDailyForecast(latitude : String, longitude : String) =
        inter.getDailyForecast(latitude, longitude)
    suspend fun getHourlyForecast(latitude : String, longitude : String) =
        inter.getHourlyForecast(latitude, longitude)

    //database part - use getCurrentWeather(), getHourlyWeather(), and getDailyWeather() in the viewmodels
    suspend fun insertCurrentWeather(currentWeather: CurrentWeather){
        db?.insertCurrentWeather(currentWeather)
    }

    suspend fun insertHourlyWeather(hourlyWeather: HourlyWeather){
        db?.insertHourlyWeather(hourlyWeather)
    }

    suspend fun insertDailyWeather(dailyWeather: DailyWeather){
        db?.insertDailyWeather(dailyWeather)
    }

    fun getCurrentWeather() : LiveData<List<CurrentWeather>>? {
        return db?.getCurrentWeather()
    }

    fun getHourlyWeather() : LiveData<List<HourlyWeather>>? {
        return db?.getHourlyWeather()
    }

    fun getDailyWeather() : LiveData<List<DailyWeather>>? {
        return db?.getDailyWeather()
    }

    fun updateWeather(latitude : String, longitude : String) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = getWeather(latitude, longitude)
            if (res.isSuccessful) {

                var json = res.body()
                val dbHelper = JsonDbHelper()

                val currentWeather = dbHelper.toCurrentWeather(json!!)
                val dailyWeatherList = dbHelper.toListDailyWeather(json)
                val hourlyWeather = dbHelper.toListHourlyWeather(json)

                //clear the DB, clearing/inserting is faster than updating
                db?.clearDailyWeather()
                db?.clearHourlyWeather()
                db?.clearDailyWeather()

                //insert items into database
                insertCurrentWeather(currentWeather)
                for(item in dailyWeatherList){
                    insertDailyWeather(item)
                }
                for(item in hourlyWeather){
                    insertHourlyWeather(item)
                }

            }
        }
    }


}