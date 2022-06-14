package com.example.weatherapp.api


import com.example.weatherapp.database.CurrentWeather
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetroApiInterface {
    @Headers("appid: b25aeba7bea92da33d3f554d3b4c3501")

    @GET("/data/2.5/onecall?")
    suspend fun getWeather(@Query("lat") latitude : String, @Query("lon") longitude : String,
                           @Query("units") units: String): Response<String>

    companion object {
        val BASE_URL = "https://api.openweathermap.org"
        fun create() : RetroApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RetroApiInterface::class.java)
        }
    }
}