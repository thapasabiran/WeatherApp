package com.example.weatherapp.database

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JsonDbHelper {
    fun onSuccess(json: String){




        //add the Weather items to the DB
        CoroutineScope(Dispatchers.IO).launch{

        }
    }

    fun getJsonFromAssets(fileName: String, context: Context): String? {
        /*
        open file - stream of bytes
        buffer - a byte array, read bytes from the file
         */
        var jsonString: String? = null
        val inStream = context.assets.open("MockData.json")
        val size = inStream.available()
        val buffer = ByteArray(size)
        inStream.read(buffer)
        inStream.close()
        jsonString = String(buffer, Charsets.UTF_8)
        return jsonString
    }
}