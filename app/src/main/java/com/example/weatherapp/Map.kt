package com.example.weatherapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.WeatherViewModel
import com.example.weatherapp.database.Util
import com.example.weatherapp.databinding.ActivityMapBinding
import com.example.weatherapp.databinding.ActivitySearchBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class Map : AppCompatActivity(), OnMapReadyCallback {
    lateinit var gMap: GoogleMap
    lateinit var binding: ActivityMapBinding
    lateinit var repo: WeatherRepository
    lateinit var vm: WeatherViewModel
    var pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
    var lat = 0.0
    var long = 0.0
    var marker : Marker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
//        var pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        var unit = pref.getString("units","C") //ToDo: C and F changed to metric and imperial

        repo = WeatherRepository(RetroApiInterface.create(),this)
        vm = WeatherViewModel(repo)
        vm.currentWeather.observe(this) {
            binding.locationText.text = "Location"
            if (unit == "F")
                binding.temperatureText.text = Util.kelvinToFahrenheit(it.temp) + " F"
            else
                binding.temperatureText.text = Util.kelvinToCelsius(it.temp) + " C"

        }

        binding.selectLocation.setOnClickListener {
            val frontPageIntent = Intent(this, SearchActivity::class.java)
            startActivity(frontPageIntent)
            frontPageIntent.putExtra("lat",lat)
            frontPageIntent.putExtra("long",long)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        gMap.setOnMapClickListener { latlng ->
            lat = latlng.latitude
            long = latlng.longitude
            val location = LatLng(latlng.latitude, latlng.longitude)
            if (marker != null)
                marker!!.remove()
            marker = gMap.addMarker(MarkerOptions().position(location))
//            val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            //Added units so that we can make api call based on units
            vm.getWeather(lat.toString(),long.toString())
        }
    }
}

