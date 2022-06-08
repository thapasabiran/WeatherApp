package com.example.weatherapp.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.database.HourlyWeather
import com.example.weatherapp.databinding.DailyWeatherLayoutBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.util.*

class DailyWeatherAdapter(private var dailyWeatherList: List<DailyWeather>): RecyclerView.Adapter<DailyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        var binding: DailyWeatherLayoutBinding = DailyWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return DailyWeatherViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val dailyWeatherItemVM = dailyWeatherList[position]


//        val date = Date(dailyWeatherItemVM.dt)
        val date = Instant.ofEpochSecond(dailyWeatherItemVM.dt)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        holder.day.text = date.dayOfWeek.toString()//.slice(0..2)
        Picasso.get().load("https://openweathermap.org/img/wn/" + dailyWeatherItemVM.icon + "@4x.png").into(holder.icon)
        holder.tempLow.text = dailyWeatherItemVM.temp_min.toString()
        holder.tempHigh.text = dailyWeatherItemVM.temp_max.toString()
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