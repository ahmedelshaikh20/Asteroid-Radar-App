package com.udacity.asteroidradar.main

import android.util.Log
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
import javax.security.auth.callback.Callback

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


    var List = service.FetchAsteroids(Constants.API_KEY)
      Log.i("NetworkAteroid" , List.toString() )}
      catch (e :Throwable){
e.printStackTrace()
      }

    }
  }





}
