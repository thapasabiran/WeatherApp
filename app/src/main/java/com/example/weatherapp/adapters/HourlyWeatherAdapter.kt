package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.Main
import com.example.weatherapp.databinding.HourlyWeatherLayoutBinding

class HourlyWeatherAdapter(private val dailyWeatherList: List<Main>): RecyclerView.Adapter<HourlyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        var binding: HourlyWeatherLayoutBinding = HourlyWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val dailyWeatherItemVM = dailyWeatherList[position]
//        holder.day = dailyWeatherItemVM.
        holder.tempLow.text = dailyWeatherItemVM.tempMin.toString()
        holder.tempHigh.text = dailyWeatherItemVM.tempMax.toString()
    }

    override fun getItemCount(): Int {
        return dailyWeatherList.size
    }
}

class HourlyWeatherViewHolder(binding: HourlyWeatherLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    //    var day = binding.forecastDayTextView
//    var day = binding.forecastDayTextView
    var tempLow = binding.forecastHourLowTextView
    var tempHigh = binding.forecastHourHighTextView
}