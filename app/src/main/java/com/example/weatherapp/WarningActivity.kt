package com.example.weatherapp

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.AlertAdapter
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.api.WeatherViewModel
import com.example.weatherapp.databinding.ActivityForecastBinding
import com.example.weatherapp.databinding.ActivityWarningBinding

class WarningActivity : AppCompatActivity() {
    lateinit var vm : WeatherViewModel
    lateinit var binding : ActivityWarningBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWarningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val api = RetroApiInterface.create()
        val repo = WeatherRepository(api, this)
        vm = WeatherViewModel(repo)


        vm.alertList?.observe(this) {
            val recyclerView: RecyclerView = binding.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(this)
            var alertList = it as ArrayList
            if (alertList.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.noAlerts.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.noAlerts.visibility = View.GONE
                var adapter = AlertAdapter(alertList)
                recyclerView.adapter = adapter
            }
        }

        binding.backButton.setOnClickListener {
            val frontPageIntent = Intent(this, FrontPageActivity::class.java)
            startActivity(frontPageIntent)
            finish()
        }
    }

}