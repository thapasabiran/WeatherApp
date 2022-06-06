package com.example.weatherapp.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JsonDbHelper {
    fun onSuccess(json: String){




        //add the Weather items to the DB
        CoroutineScope(Dispatchers.IO).launch{

        }
    }
}