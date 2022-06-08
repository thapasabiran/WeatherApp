package com.example.weatherapp.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.database.HourlyWeather
import com.example.weatherapp.databinding.HourlyWeatherLayoutBinding
import com.squareup.picasso.Picasso
import java.time.Instant
import java.time.ZoneId
import java.util.*

class HourlyWeatherAdapter(private var hourlyWeatherList: List<HourlyWeather>): RecyclerView.Adapter<HourlyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        var binding: HourlyWeatherLayoutBinding = HourlyWeatherLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return HourlyWeatherViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val hourlyWeatherItemVM = hourlyWeatherList[position]
//        holder.day = dailyWeatherItemVM.


        //ToDo: Need to confirm if it getting current hours
//        val date = Date(hourlyWeatherItemVM.dt)
        val date = Instant.ofEpochSecond(hourlyWeatherItemVM.dt)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

//        val date = Date()
//        val toLonDate: Long = date.time
//        val cal = Calendar.getInstance()
//        cal.time = date

        holder.hour.text = date.hour.toString()//cal.get(Calendar.HOUR_OF_DAY).toString()//format.format(date).toString()
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