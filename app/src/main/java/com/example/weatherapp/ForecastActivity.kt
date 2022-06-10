package com.example.weatherapp

import android.content.Context
import android.os.Bundle
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.DailyWeatherAdapter
import com.example.weatherapp.adapters.HourlyWeatherAdapter
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.WeatherViewModel
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.database.HourlyWeather
import com.example.weatherapp.databinding.ActivityForecastBinding


class ForecastActivity : AppCompatActivity() {
    lateinit var binding: ActivityForecastBinding
    lateinit var dailyWeatherList: ArrayList<DailyWeather>
    lateinit var hourlyWeatherList: ArrayList<HourlyWeather>
    lateinit var dailyWeatherAdapter: DailyWeatherAdapter
    lateinit var hourlyWeatherAdapter: HourlyWeatherAdapter
    lateinit var loadingDialog: LoadingDialog
    lateinit var vm : WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadingDialog = LoadingDialog(this)

        binding.forecastWetConTextView.text = intent.getStringExtra("weatherCondition")
        binding.forecastTemTextView.text = intent.getStringExtra("temp")
        binding.forecastLocTextView.text = intent.getStringExtra("location")

        val api = RetroApiInterface.create()
        val repo = WeatherRepository(api, this)

        vm = WeatherViewModel(repo)

        dailyWeatherList = ArrayList<DailyWeather>()
        loadingDialog.startLoadingDialog()
        vm.getDailyWeather()?.observe(this) {
            dailyWeatherList = it as ArrayList<DailyWeather> /* = java.util.ArrayList<com.example.weatherapp.database.DailyWeather> */
            dailyWeatherAdapter.setDailyWeather(dailyWeatherList)
        }

        hourlyWeatherList = ArrayList<HourlyWeather>()
        vm.getHourlyWeather()?.observe(this) {
            hourlyWeatherList = it as ArrayList<HourlyWeather> /* = java.util.ArrayList<com.example.weatherapp.database.DailyWeather> */
            hourlyWeatherAdapter.setHourlyWeather(hourlyWeatherList)
//            loadingDialog.dismissDialog()
        }

        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        var dailyRecyclerView = binding.dailyRecyclerView
        dailyWeatherAdapter = DailyWeatherAdapter(dailyWeatherList, pref)
        dailyRecyclerView.layoutManager = LinearLayoutManager(this)
        dailyRecyclerView.adapter = dailyWeatherAdapter


        var hourlyRecyclerView = binding.hourlyRecyclerView
        hourlyWeatherAdapter = HourlyWeatherAdapter(hourlyWeatherList, pref)
        hourlyRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true)
        hourlyRecyclerView.adapter = hourlyWeatherAdapter
        hourlyRecyclerView
            .getViewTreeObserver()
            .addOnGlobalLayoutListener(
                object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        // At this point the layout is complete and the
                        // dimensions of recyclerView and any child views
                        // are known.

                        hourlyRecyclerView
                            .getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this)

                        loadingDialog.dismissDialog()
                    }
                })
    }
}