package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import java.io.Console

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
    Log.i("Lol" , list.toString())
    _asteroid.value = asteroid
    _asteroids.value = list


  }




}
