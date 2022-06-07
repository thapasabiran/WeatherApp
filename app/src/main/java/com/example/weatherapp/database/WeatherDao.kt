package com.example.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeatherDao {
    @Query("SELECT * FROM CurrentWeather")
    fun getCurrentWeather() : LiveData<List<CurrentWeather>>?

    @Query("SELECT * FROM HourlyWeather")
    fun getHourlyWeather() : LiveData<List<HourlyWeather>>?

    @Query("SELECT * FROM DailyWeather")
    fun getDailyWeather() : LiveData<List<DailyWeather>>?

    @Insert
    fun insertCurrentWeather(currentWeather: CurrentWeather)

    @Insert
    fun insertHourlyWeather(hourlyWeather: HourlyWeather)

    @Insert
    fun insertDailyWeather(dailyWeather: DailyWeather)

    @Update
    fun updateCurrentWeather(currentWeather: CurrentWeather)

    @Update
    fun updateHourlyWeather(hourlyWeather: HourlyWeather)

    @Update
    fun updateDailyWeather(dailyWeather: DailyWeather)

    @Query("DELETE FROM CurrentWeather")
    fun clearCurrentWeather()

    @Query("DELETE FROM HourlyWeather")
    fun clearHourlyWeather()

    @Query("DELETE FROM DailyWeather")
    fun clearDailyWeather()
}