package com.example.weatherapp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
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
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import retrofit2.Response
import java.util.*


@RunWith(RobolectricTestRunner::class)
class WeatherRepositoryTest {


    @Mock
    lateinit var dao: WeatherDao
    @Mock
    lateinit var inter :RetroApiInterface
    @Mock
    lateinit var context : Context

    lateinit var repo: WeatherRepository

    @Before()
    fun setUp(){
        MockitoAnnotations.openMocks(this)

        context = ApplicationProvider.getApplicationContext<Context>()

        repo = WeatherRepository(inter,context)


    }
    @Test
    fun getWeatherTest(){
        var fakeList =""


        runBlocking {

            Mockito.`when`(repo.getWeather("",""))
                .thenReturn(Response.success(fakeList))


            var result = repo.getWeather("","").body()
            Assert.assertEquals(fakeList, result)
        }


    }

}