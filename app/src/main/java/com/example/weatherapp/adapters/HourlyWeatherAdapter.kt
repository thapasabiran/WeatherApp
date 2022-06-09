package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.HourlyWeather
import com.example.weatherapp.databinding.HourlyWeatherLayoutBinding
import java.util.*

class HourlyWeatherAdapter(private val dailyWeatherList: List<HourlyWeather>): RecyclerView.Adapter<HourlyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        var binding: HourlyWeatherLayoutBinding = HourlyWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return HourlyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val dailyWeatherItemVM = dailyWeatherList[position]
//        holder.day = dailyWeatherItemVM.


        //ToDo: Need to confirm if it getting current hours
        val date = Date(dailyWeatherItemVM.dt)
//        val date = Date()
//        val toLonDate: Long = date.time
        val cal = Calendar.getInstance()
        cal.time = date

        holder.hour.text = cal.get(Calendar.HOUR_OF_DAY).toString()//format.format(date).toString()
        holder.temp.text = dailyWeatherItemVM.temp.toString()
    }

    override fun getItemCount(): Int {
        return dailyWeatherList.size
    }
}

class HourlyWeatherViewHolder(binding: HourlyWeatherLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    //    var day = binding.forecastDayTextView
//    var day = binding.forecastDayTextView
    var hour = binding.forecastHourTextView
    var temp = binding.forecastTempTextView
}