package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.Database.AsteroidRepositry
import com.udacity.asteroidradar.Database.asDomainModel
import com.udacity.asteroidradar.Database.getInstance
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.parseImageofTheDay
import com.udacity.asteroidradar.api.service
import com.udacity.asteroidradar.model.ImgOfTheDay
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel(application: Application) : AndroidViewModel(application) {

  private val database = getInstance(application.applicationContext)
  private val AsteroidRepository = AsteroidRepositry(database)

  private var _asteroid = MutableLiveData<Asteroid>()
  val asteroid : LiveData<Asteroid>
  get() = _asteroid

  private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()

  val navigateToSelectedAsteroid: LiveData<Asteroid>
    get() = _navigateToSelectedAsteroid


  private val _imageDay = MutableLiveData<ImgOfTheDay>()
  val imageDay : LiveData<ImgOfTheDay>
  get() = _imageDay

  //val asteroids = AsteroidRepository.asteroids

  val asteroids : LiveData<List<Asteroid>>?
    get() = AsteroidRepository.asteroids



  init {
    viewModelScope.launch {
      AsteroidRepository.refreshAsteroids()
    }
    getImageOfTheDay()
  }

  private fun getImageOfTheDay() {
    viewModelScope.launch {
      try {

        var StringRespnse = service.FetchImgOfTheDay(Constants.API_KEY)
        var imageoftheday = parseImageofTheDay(JSONObject(StringRespnse.body().toString()))
        if(imageoftheday.media_type == "image"){
        _imageDay.value = imageoftheday;}
        else _imageDay.value = null;

      }catch (e : Exception){
        e.printStackTrace()
      }
    }
  }

 fun UpdateAsteroidsList (type :String){
   viewModelScope.launch {

     Log.i("FromMainModel" , type)

  AsteroidRepository.getAsteroids(type)}
}
  fun DisplayAsteroidDetail(asteroid: Asteroid){
    _navigateToSelectedAsteroid.value = asteroid;
  }


  fun DisplayAsteroidDetailCompleted(asteroid: Asteroid){
    _navigateToSelectedAsteroid.value = null;
  }






  class Factory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(app) as T
      }
      throw IllegalArgumentException("Unable to construct viewmodel")
    }
  }

}
