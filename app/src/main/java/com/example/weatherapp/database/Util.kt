package com.example.weatherapp.database

import android.content.Context
import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*
import java.util.prefs.Preferences

class Util {
    companion object{
        fun timestampToDateFull(l : Long) : String{
            var s = ""

            val simpleDateFormat = SimpleDateFormat("MMM dd, yyyy")
            simpleDateFormat.timeZone = TimeZone.getDefault()
            s = simpleDateFormat.format(l * 1000)

            return s
        }

        fun timestampToDayOfWeek(l : Long) : String {
            var s = ""

            val simpleDateFormat = SimpleDateFormat("EEEE")
            simpleDateFormat.timeZone = TimeZone.getDefault()
            s = simpleDateFormat.format(l * 1000)

            return s
        }

        fun timestampToDayOfWeekShort(l : Long) : String {
            var s = ""

            val simpleDateFormat = SimpleDateFormat("EEE")
            simpleDateFormat.timeZone = TimeZone.getDefault()
            s = simpleDateFormat.format(l * 1000)

            return s
        }

        fun timestampToDateShort(l : Long) : String{
            var s = ""

            val simpleDateFormat = SimpleDateFormat("MMM dd")
            simpleDateFormat.timeZone = TimeZone.getDefault()
            s = simpleDateFormat.format(l * 1000)

            return s
        }

        fun timestampToTime12(l : Long) : String {
            var s = ""

            val simpleDateFormat = SimpleDateFormat("hh:mm a")
            simpleDateFormat.timeZone = TimeZone.getDefault()
            s = simpleDateFormat.format(l * 1000)

            return s
        }

        fun timestampToTime24(l : Long) : String {
            var s = ""

            val simpleDateFormat = SimpleDateFormat("HH:mm")
            simpleDateFormat.timeZone = TimeZone.getDefault()
            s = simpleDateFormat.format(l * 1000)

            return s
        }

        fun toUserSelectedUnit(pref: SharedPreferences, temp: Double): String {
            var userPreferedTemp = ""
            if (pref.getString("units", "K").equals("C")) {
                var celcius = temp - 273.15
                userPreferedTemp = celcius.toInt().toString() + " C"
            } else if (pref.getString("units", "K").equals("F")) {
                var fahrenheit = 1.8*(temp - 273.15) + 32
                userPreferedTemp = fahrenheit.toInt().toString() + " F"
            } else {
                userPreferedTemp = temp.toInt().toString() + " K"
            }
            return userPreferedTemp
        }
    }
}