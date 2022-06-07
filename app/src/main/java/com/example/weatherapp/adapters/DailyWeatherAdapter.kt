package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.databinding.DailyWeatherLayoutBinding

class DailyWeatherAdapter(private val dailyWeatherList: List<DailyWeather>): RecyclerView.Adapter<DailyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        var binding: DailyWeatherLayoutBinding = DailyWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return DailyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val dailyWeatherItemVM = dailyWeatherList[position]
//        holder.day = dailyWeatherItemVM.
        holder.tempLow.text = dailyWeatherItemVM.temp_min.toString()
        holder.tempHigh.text = dailyWeatherItemVM.temp_max.toString()
    }

    override fun getItemCount(): Int {
        return dailyWeatherList.size
    }
}

class DailyWeatherViewHolder(binding: DailyWeatherLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
//    var day = binding.forecastDayTextView
//    var day = binding.forecastDayTextView
    var tempLow = binding.forecastTempLowTextView
    var tempHigh = binding.forecastTempHighTextView
}