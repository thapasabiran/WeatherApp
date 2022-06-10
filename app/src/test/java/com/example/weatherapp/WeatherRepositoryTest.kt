package com.example.weatherapp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.api.RetroApiInterface
import com.example.weatherapp.api.WeatherRepository
import com.example.weatherapp.database.DailyWeather
import com.example.weatherapp.database.WeatherDao
import io.reactivex.rxjava3.core.Observable.fromArray
import io.reactivex.rxjava3.core.Single
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Response
import java.util.*


@RunWith(JUnit4::class)
class WeatherRepositoryTest {

    lateinit var repo: WeatherRepository

    @Mock
    lateinit var dao: WeatherDao
   @Mock
    lateinit var inter :RetroApiInterface
    @Mock
    lateinit var context : Context

    @Before()
    fun setUp(){
        repo = WeatherRepository(inter,context)


    }
    @Test
    suspend fun getWeatherTest(){
   var fakeList =""


        runBlocking {

            Mockito.`when`(inter.getWeather("",""))
                .thenReturn(Response.success(fakeList))


            var result = repo.getWeather("","")
            Assert.assertEquals(fakeList, result.body())
      }


    }
    @Test
    fun `given repository when calling Weatherlist then list is empty and assert its empty`() {

    }

}