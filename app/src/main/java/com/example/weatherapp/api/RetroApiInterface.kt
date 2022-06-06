package com.example.weatherapp.api

import com.example.weatherapp.Weather
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroApiInterface {
    @GET("/data/2.5/weather?")
    suspend fun getWeather(@Query("lat") latitude : String, @Query("lon") longitude : String,
                           @Query("appid") apikey : String = "b25aeba7bea92da33d3f554d3b4c3501"): Response<Weather>


    companion object {
        val BASE_URL = "https://api.openweathermap.org"
        fun create() : RetroApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RetroApiInterface::class.java)
        }
    }
}