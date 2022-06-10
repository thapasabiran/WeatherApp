package com.example.weatherapp

import android.os.Bundle
import android.widget.AbsListView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    var isScrolling = false
    var curentItems = 0
    var totalItems = 0
    var scrollOutItems = 0
    var offSet = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forecastWetConTextView.text = intent.getStringExtra("weatherCondition")
        binding.forecastTemTextView.text = intent.getStringExtra("temp")
        binding.forecastLocTextView.text = intent.getStringExtra("location")

        val api = RetroApiInterface.create()
        val repo = WeatherRepository(api, this)
        loadingDialog = LoadingDialog(this)

        vm = WeatherViewModel(repo)

        dailyWeatherList = ArrayList<DailyWeather>()
        var dailyRecyclerView = binding.dailyRecyclerView
        dailyWeatherAdapter = DailyWeatherAdapter(dailyWeatherList)
        dailyRecyclerView.layoutManager = LinearLayoutManager(this)
        dailyRecyclerView.adapter = dailyWeatherAdapter

        //start loading dialog
        loadingDialog.startLoadingDialog()
        //get the daily weather and bind it to the recycler view adapter
        vm.getDailyWeather()?.observe(this) {
            dailyWeatherList = it as ArrayList<DailyWeather> /* = java.util.ArrayList<com.example.weatherapp.database.DailyWeather> */
            //To get min and max temp of today weather we are using the first element in list that contains today weather data
            binding.forecastLowTextView.text = dailyWeatherList[0].temp_min.toString()
            binding.forecastHighTextView.text = dailyWeatherList[0].temp_max.toString()
            dailyWeatherAdapter.setDailyWeather(dailyWeatherList)
        }

        //Get the first 12 hourly weather report and after that only load more if user start scrolling for more data
        hourlyWeatherList = ArrayList<HourlyWeather>()
        vm.getHourlyWeather(12, offSet)?.observe(this) {
            hourlyWeatherList = it as ArrayList<HourlyWeather> /* = java.util.ArrayList<com.example.weatherapp.database.DailyWeather> */
            hourlyWeatherAdapter.setHourlyWeather(hourlyWeatherList)
        }

        var hourlyRecyclerView = binding.hourlyRecyclerView
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        hourlyWeatherAdapter = HourlyWeatherAdapter(hourlyWeatherList)
        hourlyRecyclerView.layoutManager = layoutManager
        hourlyRecyclerView.adapter = hourlyWeatherAdapter

        //if user start scrolling for more data, get it from db based on offset
        hourlyRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                curentItems = layoutManager.childCount
                totalItems = layoutManager.itemCount
                scrollOutItems = layoutManager.findFirstVisibleItemPosition()
                //dismiss loading dialog
                loadingDialog.dismissDialog()

                //Size of hourForecast table is 48, we can also get size and use it
                if (isScrolling && ((curentItems + scrollOutItems) == totalItems) && offSet < 48) {
                    isScrolling = false
                    offSet += 12 //increment offset to get 12 more data once we reach end of current 12 data
                    //start loading dialog until extra 12 data get fetched
                    loadingDialog.startLoadingDialog()
                    vm.getHourlyWeather(12, offSet)?.observe(this@ForecastActivity) {
                        hourlyWeatherList.addAll(it as ArrayList<HourlyWeather>) /* = java.util.ArrayList<com.example.weatherapp.database.DailyWeather> */
                        hourlyWeatherAdapter.setHourlyWeather(hourlyWeatherList)
                        //dismiss loading dialog
                        loadingDialog.dismissDialog()
                    }
                }
            }
        })
    }
}