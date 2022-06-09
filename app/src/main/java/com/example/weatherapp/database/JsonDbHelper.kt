package com.example.weatherapp.database

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

class JsonDbHelper {

    fun toCurrentWeather(json: String) : CurrentWeather{
        val jsonObj = JSONObject(json)

        if(jsonObj.has("current")) {
            val current = jsonObj.getJSONObject("current")

            println(current.toString())

            return CurrentWeather(
                null,
                dt = current.getLong("dt"),
                sunrise = current.getLong("sunrise"),
                sunset = current.getLong("sunset"),
                temp = current.getDouble("temp"),
                feels_like = current.getDouble("feels_like"),
                pressure = current.getInt("pressure"),
                humidity = current.getInt("humidity"),
                dew_point = current.getDouble("dew_point"),
                uvi = current.getDouble("uvi"),
                clouds = current.getInt("clouds"),
                visibility = current.getInt("visibility"),
                wind_speed = current.getDouble("wind_speed"),
                wind_deg = current.getInt("wind_deg"),
                weather_id = current.getJSONArray("weather").getJSONObject(0).getInt("id"),
                short_description = current.getJSONArray("weather").getJSONObject(0)
                    .getString("main"),
                long_description = current.getJSONArray("weather").getJSONObject(0)
                    .getString("description"),
                icon = current.getJSONArray("weather").getJSONObject(0).getString("icon"),
            )
        } else {
            return CurrentWeather(null,
                0,
                0,
                0,
                0.0,
                0.0,
                0,
                0,
                0.0,
                0.0,
                0,
                0,
                0.0,
                0,
                0,
                "N/A",
                "N/A",
                "02d")
        }
    }

    fun toListHourlyWeather(json : String) : List<HourlyWeather>{
        var jObject = JSONObject(json)
        val list = ArrayList<HourlyWeather>()

        if(jObject.has("hourly")) {
            var hourly = jObject.getJSONArray("hourly")

            // {"dt":1654524000,"temp":16.79,"feels_like":16.67,"pressure":1014,"humidity":82,"dew_point":13.7,"uvi":0.36,"clouds":5,"visibility":10000,"wind_speed":0.33,"wind_deg":306,"wind_gust":1.22,"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"pop":0}
            (0 until hourly.length()).mapTo(list) {
                HourlyWeather(
                    null,
                    hourly.getJSONObject(it).getLong("dt"),
                    hourly.getJSONObject(it).getDouble("temp"),
                    hourly.getJSONObject(it).getDouble("feels_like"),
                    hourly.getJSONObject(it).getInt("pressure"),
                    hourly.getJSONObject(it).getInt("humidity"),
                    hourly.getJSONObject(it).getDouble("dew_point"),
                    hourly.getJSONObject(it).getDouble("uvi"),
                    hourly.getJSONObject(it).getInt("clouds"),
                    hourly.getJSONObject(it).getInt("visibility"),
                    hourly.getJSONObject(it).getDouble("wind_speed"),
                    hourly.getJSONObject(it).getInt("wind_deg"),
                    hourly.getJSONObject(it).getDouble("wind_gust"),
                    hourly.getJSONObject(it).getJSONArray("weather").getJSONObject(0).getInt("id"),
                    hourly.getJSONObject(it).getJSONArray("weather").getJSONObject(0)
                        .getString("main"),
                    hourly.getJSONObject(it).getJSONArray("weather").getJSONObject(0)
                        .getString("description"),
                    hourly.getJSONObject(it).getJSONArray("weather").getJSONObject(0)
                        .getString("icon"),
                    hourly.getJSONObject(it).getDouble("pop")
                )
            }
        }

        return list
    }

    fun toListDailyWeather( json : String) : List<DailyWeather>{
        var jObject = JSONObject(json)
        val list = ArrayList<DailyWeather>()

        if(jObject.has("daily")) {
            var daily = jObject.getJSONArray("daily")

            //{"dt":1655150400,"sunrise":1655124019,"sunset":1655177460,"moonrise":1655176320,"moonset":1655120520,"moon_phase":0.47,"temp":{"day":31.29,"min":15.72,"max":33.07,"night":22.03,"eve":28.35,"morn":19.77},"feels_like":{"day":29.17,"night":20.84,"eve":26.87,"morn":19.26},"pressure":1012,"humidity":11,"dew_point":-2.22,"wind_speed":5.68,"wind_deg":329,"wind_gust":6.86,"weather":[{"id":800,"main":"Clear","description":"clear sky","icon":"01d"}],"clouds":0,"pop":0.04,"uvi":10}]}
            (0 until daily.length()).mapTo(list) {
                DailyWeather(
                    null,
                    daily.getJSONObject(it).getLong("dt"),
                    daily.getJSONObject(it).getLong("sunrise"),
                    daily.getJSONObject(it).getLong("sunset"),
                    daily.getJSONObject(it).getLong("moonrise"),
                    daily.getJSONObject(it).getLong("moonset"),
                    daily.getJSONObject(it).getDouble("moon_phase"),
                    daily.getJSONObject(it).getJSONObject("temp").getDouble("min"),
                    daily.getJSONObject(it).getJSONObject("temp").getDouble("max"),
                    daily.getJSONObject(it).getJSONObject("temp").getDouble("day"),
                    daily.getJSONObject(it).getJSONObject("temp").getDouble("night"),
                    daily.getJSONObject(it).getJSONObject("temp").getDouble("eve"),
                    daily.getJSONObject(it).getJSONObject("temp").getDouble("morn"),
                    daily.getJSONObject(it).getInt("pressure"),
                    daily.getJSONObject(it).getInt("humidity"),
                    daily.getJSONObject(it).getDouble("dew_point"),
                    daily.getJSONObject(it).getDouble("wind_speed"),
                    daily.getJSONObject(it).getInt("wind_deg"),
                    daily.getJSONObject(it).getDouble("wind_gust"),
                    daily.getJSONObject(it).getJSONArray("weather").getJSONObject(0).getInt("id"),
                    daily.getJSONObject(it).getJSONArray("weather").getJSONObject(0)
                        .getString("main"),
                    daily.getJSONObject(it).getJSONArray("weather").getJSONObject(0)
                        .getString("description"),
                    daily.getJSONObject(it).getJSONArray("weather").getJSONObject(0)
                        .getString("icon"),
                    daily.getJSONObject(it).getInt("clouds"),
                    daily.getJSONObject(it).getDouble("pop"),
                    if (daily.getJSONObject(it).has("rain")) daily.getJSONObject(it)
                        .getDouble("rain") else 0.0,
                    if (daily.getJSONObject(it).has("snow")) daily.getJSONObject(it)
                        .getDouble("snow") else 0.0,
                    daily.getJSONObject(it).getDouble("uvi")
                )
            }
        }
        return list
    }

    fun toAlerts(json: String) : List<Alert>{
        var jObject = JSONObject(json)
        val list = ArrayList<Alert>()
        if(jObject.has("alerts")) {
            var alerts = jObject.getJSONArray("alerts")

            (0 until alerts.length()).mapTo(list) {
                Alert(
                    null,
                    alerts.getJSONObject(it).getString("sender_name"),
                    alerts.getJSONObject(it).getString("event"),
                    alerts.getJSONObject(it).getString("description"),
                    alerts.getJSONObject(it).getLong("start"),
                    alerts.getJSONObject(it).getLong("end")
                )
            }
        }
        return list
    }
}