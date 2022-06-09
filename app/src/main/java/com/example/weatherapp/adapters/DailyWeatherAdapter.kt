package com.example.weatherapp.adapters

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.database.HourlyWeather
import com.example.weatherapp.database.Util.Companion.timestampToDayOfWeekShort
import com.example.weatherapp.database.Util.Companion.toUserSelectedUnit
import com.example.weatherapp.databinding.DailyWeatherLayoutBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*

class DailyWeatherAdapter(private var dailyWeatherList: List<DailyWeather>, private var preferences: SharedPreferences): RecyclerView.Adapter<DailyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        var binding: DailyWeatherLayoutBinding = DailyWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return DailyWeatherViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val dailyWeatherItemVM = dailyWeatherList[position]
        holder.day.text = timestampToDayOfWeekShort(dailyWeatherItemVM.dt)

        //Load the icon from open weather api based on icon
        Picasso.get().load("https://openweathermap.org/img/wn/" + dailyWeatherItemVM.icon + "@4x.png").into(holder.icon)

//        //ToDo: Convert to user preferred unit
//        if (preferences.getString("units", "K").equals("C")) {
//            var celcius = dailyWeatherItemVM.temp_min - 273.15
//            holder.tempLow.text = celcius.toInt().toString()
//        } else if (preferences.getString("units", "K").equals("F")) {
//            var fahrenheit = 1.8*(dailyWeatherItemVM.temp_min-273.15) + 32
//            holder.tempLow.text = fahrenheit.toInt().toString()
//        } else {
//            holder.tempLow.text = dailyWeatherItemVM.temp_min.toString()
//        }

        holder.tempLow.text = toUserSelectedUnit(preferences, dailyWeatherItemVM.temp_min)

//        //ToDo: Convert to user preferred unit
////        preferences.getString("units", )
//        if (preferences.getString("units", "default").equals("C")) {
//            var celcius = dailyWeatherItemVM.temp_max - 273.15
//            holder.tempHigh.text = celcius.toInt().toString()
//        } else if (preferences.getString("units", "default").equals("F")){
//            var fahrenheit = 1.8*(dailyWeatherItemVM.temp_max-273.15) + 32
//            holder.tempHigh.text = fahrenheit.toInt().toString()
//        } else {
//            holder.tempLow.text = dailyWeatherItemVM.temp_max.toString()
//        }

        holder.tempLow.text = toUserSelectedUnit(preferences, dailyWeatherItemVM.temp_max)


//        holder.tempLow.text = dailyWeatherItemVM.temp_min.toString()
//        holder.tempHigh.text = dailyWeatherItemVM.temp_max.toString()
    }

    override fun getItemCount(): Int {
        return dailyWeatherList.size
    }

    fun setDailyWeather(dailyWeatherList: List<DailyWeather>) {
        this.dailyWeatherList = dailyWeatherList
        notifyDataSetChanged()
    }
}

class DailyWeatherViewHolder(binding: DailyWeatherLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    var day = binding.forecastDayTextView
    var icon = binding.forecastImage
    var tempLow = binding.forecastTempLowTextView
    var tempHigh = binding.forecastTempHighTextView
}