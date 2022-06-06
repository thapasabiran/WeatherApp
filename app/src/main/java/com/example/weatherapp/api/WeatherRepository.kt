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

    //retrofit part
    suspend fun getWeather(latitude : String, longitude : String) =
        inter.getWeather(latitude, longitude)

    //database part
    suspend fun insertCurrentWeather(currentWeather: CurrentWeather){
        db?.insertCurrentWeather(currentWeather)
    }

    suspend fun insertHourlyWeather(hourlyWeather: HourlyWeather){
        db?.insertHourlyWeather(hourlyWeather)
    }

    suspend fun insertDailyWeather(dailyWeather: DailyWeather){
        db?.insertDailyWeather(dailyWeather)
    }

    fun getCurrentWeather() : LiveData<CurrentWeather>? {
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

                println("sdjkfhsdjkfghsdjkfhsdjkfhsdfjkhsj")
                var json = res.body()
                val dbHelper = JsonDbHelper()

                val currentWeather = dbHelper.toCurrentWeather(json!!)
                println(res.body())

            }
        }
    }


}