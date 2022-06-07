package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.WeatherViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    lateinit var vm : WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//
        val api = RetroApiInterface.create()
        val repo = WeatherRepository(api, this)
        vm = WeatherViewModel(repo)
        vm.updateWeather("35","139")

        vm.weather?.observe(this, {
            item -> println("daily weather: length: ${item.size} $item")
        })

        vm.getDailyWeather()
//        vm.getHourlyForecast("35","139")

        //here's the code for the observable thing we did today - if you want, I'll switch the Livedata to Observable in the repo
        vm.getDailyWeatherObservable()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribeBy(
                onNext = {
                    println("OBSERVABLE PATTERN: size: ${it.size} $it")
                }
            )
    }
}