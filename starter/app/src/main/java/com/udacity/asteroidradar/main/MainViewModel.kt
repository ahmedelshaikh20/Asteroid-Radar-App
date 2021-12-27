package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.squareup.moshi.Json
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.service
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import javax.security.auth.callback.Callback
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {


  private var _asteroid = MutableLiveData<Asteroid>()
  val asteroid : LiveData<Asteroid>
  get() = _asteroid


  private var _asteroids = MutableLiveData<List<Asteroid>>()
  val asteroids : LiveData<List<Asteroid>>
  get() = _asteroids

  init {
    val asteroid = Asteroid(12,"lol","25/11/2020",432.0,645.0,124.0,368.0,true)
    val list = listOf(asteroid)

    getAllAsteroids()
    //var NetworkAsteroid = parseAsteroidsJsonResult()
    _asteroids.value = list


  }


  fun getAllAsteroids(){
    viewModelScope.launch {
      try {
        val formattedDate =getNextSevenDaysFormattedDates()
        val start_date = formattedDate[0]
        val end_date = formattedDate[formattedDate.size-1]
    var List = service.FetchAsteroids( start_date , end_date , Constants.API_KEY )
        Log.i("NetworkAteroid" ,List.body().toString())
        var  lol = parseAsteroidsJsonResult(JSONObject(List.body().toString()))
        Log.i("lol" ,lol.toString() )
      }
      catch (e :Throwable){
e.printStackTrace()
      }
    }




  }

  @SuppressLint("NewApi")
  private fun getNextSevenDaysFormattedDates(): ArrayList<String> {
    val formattedDateList = ArrayList<String>()

    val calendar = Calendar.getInstance()
    for (i in 0..Constants.DEFAULT_END_DATE_DAYS) {
      val currentTime = calendar.time
      val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
      formattedDateList.add(dateFormat.format(currentTime))
      calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    return formattedDateList
  }




}
