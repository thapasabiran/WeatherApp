package com.example.weatherapp

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.WeatherViewModel
import com.example.weatherapp.databinding.ActivityMainBinding
import java.util.*
import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.text.method.TextKeyListener.clear
import androidx.core.view.isVisible
import com.example.weatherapp.database.Util
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var repo : WeatherRepository
    lateinit var vm : WeatherViewModel
    lateinit var pref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        //Comment this out if you want to skip the welcome page when there's already a stored pref
        pref.edit().clear().commit()

        if(pref.getString("location","") != "") {
            val frontPageIntent = Intent(this, FrontPageActivity::class.java)
            startActivity(frontPageIntent)
        }

        val adapter = ArrayAdapter.createFromResource(this,
            R.array.temperature_units, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        repo = WeatherRepository(RetroApiInterface.create(), this)
        vm = WeatherViewModel(this,repo)

        //Need to add location validation still
        binding.welcomeContinueButton.setOnClickListener {
            binding.welcomeContinueButton.isVisible = false
            binding.loadingBar.isVisible = true
            GlobalScope.launch(Dispatchers.Main) { //Use main thread for toast to work
                initialize()
                binding.welcomeContinueButton.isVisible = true
                binding.loadingBar.isVisible = false
            }
        }


    }

    //Checks for valid location input and updates pref file and initializes roomDB
    //with all weather data for the given location
    suspend fun initialize() {
        val location = binding.welcomeLocationTextEdit.text.toString()
        if (location != "" && vm.isValidLocation(location).await()) {
            var address = vm.searchLocation(location).await()
            val lat = address!!.latitude.toString()
            val long = address!!.longitude.toString()

            //make sure pref is set before calling updateWeather
            with(pref.edit()) {
                putString("location", binding.welcomeLocationTextEdit.text.toString())
                putString("latitude", lat)
                putString("longitude", long)
                putString("units",Util.getDefaultUnits(binding.spinner.selectedItem.toString()))
                apply()
            }
            vm.updateWeather(lat, long)
            val frontPageIntent = Intent(this, FrontPageActivity::class.java)
            startActivity(frontPageIntent)
        } else {
            Toast.makeText(this, "Please enter a valid location", Toast.LENGTH_SHORT).show()
            delay(300) //stops user from spam clicking button
        }

    }
}