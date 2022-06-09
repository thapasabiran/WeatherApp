package com.example.weatherapp.api

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.database.*
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherRepository(val inter : RetroApiInterface, context: Context) {

    val db : WeatherDao? = AppDatabase.getInstance(context)?.weatherDao()

    //retrofit part - used to update the database, don't use in viewmodel
    suspend fun getWeather(latitude : String, longitude : String, units: String) =
        inter.getWeather(latitude, longitude, units)

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

    suspend fun insertAlert(alert: Alert){
        db?.insertAlert(alert)
    }

    fun getCurrentWeather() : LiveData<List<CurrentWeather>>? {
        return db?.getCurrentWeather()
    }

    fun getCurrentWeatherSingle() : LiveData<CurrentWeather>? {
        return db?.getCurrentWeatherSingle()
    }

    fun getHourlyWeather() : LiveData<List<HourlyWeather>>? {
        return db?.getHourlyWeather()
    }

    fun getDailyWeather() : LiveData<List<DailyWeather>>? {
        return db?.getDailyWeather()
    }

    fun getAlerts() : LiveData<List<Alert>>? {
        return db?.getAlerts()
    }

    //TEST observable pattern
    fun getDailyWeatherObservable() : Observable<List<DailyWeather>>? {
        return db?.getDailyWeatherObservable()
    }

    fun getCurrentWeatherObservable() : Observable<List<CurrentWeather>>? {
        return db?.getCurrentWeatherObservable()
    }

    fun getCurrentWeatherObservableSingle() : Observable<CurrentWeather>? {
        return db?.getCurrentWeatherObservableSingle()
    }

    fun getHourlyWeatherObservable() : Observable<List<HourlyWeather>>? {
        return db?.getHourlyWeatherObservable()
    }

    fun getAlertsObservable() : Observable<List<Alert>>?{
        return db?.getAlertsObservable()
    }

    fun updateWeather(latitude : String, longitude : String, units: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var res = getWeather(latitude, longitude, units)
            if (res.isSuccessful) {

                var json = res.body()
                val dbHelper = JsonDbHelper()

                val currentWeather = dbHelper.toCurrentWeather(json!!)
                val dailyWeatherList = dbHelper.toListDailyWeather(json)
                val hourlyWeather = dbHelper.toListHourlyWeather(json)

                //clear the DB, clearing/inserting is faster than updating
                db?.clearCurrentWeather()
                db?.clearHourlyWeather()
                db?.clearDailyWeather()
                db?.clearAlerts()

                //insert items into database
                insertCurrentWeather(currentWeather)
                for(item in dailyWeatherList){
                    insertDailyWeather(item)
                }
                for(item in hourlyWeather){
                    insertHourlyWeather(item)
                }

                if(json.contains("alerts")){
                    val alerts = dbHelper.toAlerts(json)
                    for(item in alerts){
                        insertAlert(item)
                    }
                }

            }
        }
    }


}