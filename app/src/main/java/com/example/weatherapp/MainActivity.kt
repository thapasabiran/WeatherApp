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
import android.location.Geocoder

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var repo : WeatherRepository
    lateinit var vm : WeatherViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Shared preferences are application-wide, getPreferences only applies to a single activity
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)

        val adapter = ArrayAdapter.createFromResource(this,
            R.array.temperature_units, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        //Need to add location validation still
        binding.welcomeContinueButton.setOnClickListener {

            with (pref.edit()) {
                putString("location", binding.welcomeLocationTextEdit.text.toString())
                putString("units", binding.spinner.selectedItem.toString())
                apply()
            }
            val frontPageIntent = Intent(this, FrontPageActivity::class.java)
            startActivity(frontPageIntent)
        }

    }
}