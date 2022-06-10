package com.example.weatherapp.adapters

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.database.Util.Companion.timestampToDayOfWeek
import com.example.weatherapp.databinding.DailyWeatherLayoutBinding
import com.squareup.picasso.Picasso

class DailyWeatherAdapter(private var dailyWeatherList: List<DailyWeather>): RecyclerView.Adapter<DailyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        var binding: DailyWeatherLayoutBinding = DailyWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return DailyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val dailyWeatherItemVM = dailyWeatherList[position]
        holder.day.text = timestampToDayOfWeek(dailyWeatherItemVM.dt)
        //Load the icon from open weather api based on icon
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