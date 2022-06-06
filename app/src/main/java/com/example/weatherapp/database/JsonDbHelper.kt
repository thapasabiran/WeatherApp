package com.example.weatherapp.database

import android.content.Context
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JsonDbHelper {
    fun onSuccess(json: String){

    }

    fun toCurrentWeather(json: String) : CurrentWeather{
        val jsonObj = JsonParser.parseString(json)
        val current = jsonObj.asJsonObject.getAsJsonObject("current")

        println("sdfhjklsdfjlfjslfsdjkl")


        return CurrentWeather(
            null,
            dt = 0,
            sunrise = 0,
            sunset = 0,
            temp = 0.0,
            feels_like = 0.0,
            pressure = 0,
            humidity = 0,
            dew_point = 0.0,
            uvi = 0.0,
            clouds = 0,
            visibility = 0,
            wind_speed = 0.0,
            wind_deg = 0,
            weather_id = 0,
            short_description = "",
            long_description = "",
            icon = "",
        )
    }

    fun getJsonFromAssets(fileName: String, context: Context): String? {
        /*
        open file - stream of bytes
        buffer - a byte array, read bytes from the file
         */
        var jsonString: String? = null
        val inStream = context.assets.open("MockData.json")
        val size = inStream.available()
        val buffer = ByteArray(size)
        inStream.read(buffer)
        inStream.close()
        jsonString = String(buffer, Charsets.UTF_8)
        return jsonString
    }
}