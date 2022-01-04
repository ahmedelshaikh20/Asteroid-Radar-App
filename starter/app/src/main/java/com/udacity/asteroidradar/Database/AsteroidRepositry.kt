package com.udacity.asteroidradar.Database

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.api.service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepositry(val database: AsteroidDatabase) {

  var asteroids: LiveData<List<Asteroid>>? =
    Transformations.map(database.asteroidDao.getAll()) {
      it?.asDomainModel()
    }


  suspend fun getAsteroids(forceNetwork: Boolean = false) {
    if (forceNetwork || asteroids?.value.isNullOrEmpty()) {
      refreshAsteroids()
    }

  }
  fun getSaved(){
     asteroids =
      Transformations.map(database.asteroidDao.getAll()) {
        it?.asDomainModel()
      }
  }
  fun getByWeek(){

    asteroids =
    Transformations.map(database.asteroidDao.getAsteroidsOfWeek(getNextSevenDaysFormattedDates())) {
      it?.asDomainModel()
    }
  }
  fun getToday(){
    asteroids =
      Transformations.map(database.asteroidDao.getAsteroidByDates(getNextSevenDaysFormattedDates().get(0))) {
        it?.asDomainModel()
      }
  }


  private suspend fun refreshAsteroids() {
    withContext(Dispatchers.IO) {
      val start_date = getNextSevenDaysFormattedDates().get(0)
      val last_date = getNextSevenDaysFormattedDates().get(7)
      val asteroidsStringResponse =
        async { service.FetchAsteroids(start_date, last_date, Constants.API_KEY) }
      val listAsteroids =
        async { parseAsteroidsJsonResult(JSONObject(asteroidsStringResponse.await().body())) }
      database.asteroidDao.insertAll(*listAsteroids.await().asDatabaseModel())
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
