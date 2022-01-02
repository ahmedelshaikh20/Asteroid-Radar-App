package com.udacity.asteroidradar.Database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.NasaService
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AsteroidRepositry (val database : AsteroidDatabase) {

  var asteroids :LiveData<List<Asteroid>>? =
    Transformations.map(database.asteroidDao.getAll() ,{
    it?.asDomainModel()
  })


  suspend fun getAsteroids(type : String) {
    Log.i("Gone Throught Today" , type)

    when(type){
      "day" -> {GetDayAsteroids(getNextSevenDaysFormattedDates().get(0))}
      "saved" -> {GetAllAsteroids()}
      "All" -> {refreshAsteroids()}
    }
  }

  fun GetDayAsteroids(date:String) {
    Log.i("Gone Throught Today" , date)

    asteroids = Transformations.map(database.asteroidDao.getAsteroidByDates(date)) {
      it?.asDomainModel()
    }

  }

   fun GetAllAsteroids(){
    asteroids =Transformations.map(database.asteroidDao.getAll() ,{
      it?.asDomainModel()
    })
  }

suspend fun refreshAsteroids(){
  withContext(Dispatchers.IO){

    val start_date = getNextSevenDaysFormattedDates().get(0)
    val last_date = getNextSevenDaysFormattedDates().get(7)
    val asteroidsStringResponse = async {  service.FetchAsteroids(start_date , last_date , Constants.API_KEY)}
    Log.i("Yeees" ,asteroidsStringResponse.await().body().toString())

    val listAsteroids = async {  parseAsteroidsJsonResult(JSONObject(asteroidsStringResponse.await().body())) }
    Log.i("Yeees" ,"Done To DataBase")

    database.asteroidDao.insertAll(*listAsteroids.await().asDatabaseModel())
    Log.i("Yeees" ,"Done To DataBase")

  }
}


}
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
