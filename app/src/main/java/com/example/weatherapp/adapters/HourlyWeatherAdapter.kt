package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.HourlyWeather
import com.example.weatherapp.database.Util.Companion.timestampToTime24
import com.example.weatherapp.databinding.HourlyWeatherLayoutBinding
import com.squareup.picasso.Picasso

class HourlyWeatherAdapter(private var hourlyWeatherList: List<HourlyWeather>): RecyclerView.Adapter<HourlyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        var binding: HourlyWeatherLayoutBinding = HourlyWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val hourlyWeatherItemVM = hourlyWeatherList[position]
        holder.hour.text = timestampToTime24(hourlyWeatherItemVM.dt)
        //Image icon is provided by open weather api based on icon value
        Picasso.get().load("https://openweathermap.org/img/wn/" + hourlyWeatherItemVM.icon + "@4x.png").into(holder.icon)
        holder.temp.text = hourlyWeatherItemVM.temp.toString()
    }

    override fun getItemCount(): Int {
        return hourlyWeatherList.size
    }

    fun setHourlyWeather(hourlyWeatherList: List<HourlyWeather>) {
        this.hourlyWeatherList = hourlyWeatherList
        notifyDataSetChanged()
    }
}

class HourlyWeatherViewHolder(binding: HourlyWeatherLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    var icon = binding.forecastHourImage
    var hour = binding.forecastHourTextView
    var temp = binding.forecastTempTextView
}