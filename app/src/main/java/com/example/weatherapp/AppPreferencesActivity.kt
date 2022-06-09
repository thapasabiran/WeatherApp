package com.example.weatherapp

import android.content.SharedPreferences
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.weatherapp.databinding.ActivityAppPreferencesBinding
import com.example.weatherapp.databinding.ActivityFrontPageBinding

class AppPreferencesActivity : AppCompatActivity() {
    val okColor = Color.parseColor("#E0E0E0")
    val errColor = Color.parseColor("#FF9D8C")
    val NO_LOC_TEXT = "No location set"

    lateinit var binding: ActivityAppPreferencesBinding
    lateinit var preferences : SharedPreferences
    lateinit var geocoder: Geocoder
    lateinit var  locationList: ArrayList<Address>
    lateinit var currLocationText : String
    var use24h = false
    var useMetricUnits = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences("prefs", MODE_PRIVATE)
        geocoder = Geocoder(this)
        locationList = ArrayList()

        //load settings and apply to controls in the xml
        currLocationText = preferences.getString("niceLocation", NO_LOC_TEXT)!!
        use24h = preferences.getBoolean("use24hourTime", false)
        useMetricUnits = preferences.getBoolean("useMetricUnits", false)

        binding.switch24h.isChecked = use24h
        binding.switchCelcius.isChecked = useMetricUnits
        binding.currentLocationLabel.setText(currLocationText)
        binding.locationTextinputlayout.boxBackgroundColor = okColor

        println(preferences.getString("niceLocation", "no location"))

        binding.applyButton.setOnClickListener {
            val searchTerm = binding.zipEditText.text.toString()
            var loc = ""
            if(searchTerm.isEmpty()){
                binding.locationTextinputlayout.boxBackgroundColor = errColor
                loc = NO_LOC_TEXT
                Toast.makeText(this, "Please enter a Postal code or a city name and state!", Toast.LENGTH_LONG).show()
            } else {
                //geocoder returns a list, so we'll use a list as well
                val locList = geocoder.getFromLocationName(searchTerm, 5)
                locationList.clear()
                locationList.addAll(locList)
                if(locationList.isEmpty()) {
                    binding.locationTextinputlayout.boxBackgroundColor = errColor
                    Toast.makeText(this, "Could not resolve the location!", Toast.LENGTH_LONG).show()
                    loc = NO_LOC_TEXT
                } else {
                    binding.locationTextinputlayout.boxBackgroundColor = okColor
                    loc = locationList.get(0).getAddressLine(0)
                    with(preferences.edit()){
                        putString("niceLocation", loc)
                        putString("latitude", locationList.get(0).latitude.toString())
                        putString("longitude", locationList.get(0).longitude.toString())
                        apply()
                    }
                }
            }

            binding.currentLocationLabel.setText(loc)
        }

        binding.switch24h.setOnCheckedChangeListener { button, isChecked ->
            with(preferences.edit()){
                putBoolean("use24hourTime", isChecked)
                apply()
            }
        }

        binding.switchCelcius.setOnCheckedChangeListener { button, isChecked ->
            with(preferences.edit()){
                putBoolean("useMetricUnits", isChecked)
                apply()
            }
        }

    }
}