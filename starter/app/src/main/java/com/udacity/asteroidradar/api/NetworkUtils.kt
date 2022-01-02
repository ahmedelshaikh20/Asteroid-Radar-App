package com.udacity.asteroidradar.api

import android.annotation.SuppressLint
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.model.ImgOfTheDay
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

fun parseAsteroidsJsonResult(jsonResult: JSONObject): ArrayList<Asteroid> {
    val nearEarthObjectsJson = jsonResult.getJSONObject("near_earth_objects")
    val asteroidList = ArrayList<Asteroid>()
    val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()
  for (formattedDate in nextSevenDaysFormattedDates) {
    val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)
        for (i in 0 until dateAsteroidJsonArray.length()) {
            val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
            val id = asteroidJson.getLong("id")
            val codename = asteroidJson.getString("name")
            val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
            val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                .getJSONObject("kilometers").getDouble("estimated_diameter_max")
            val closeApproachData = asteroidJson
                .getJSONArray("close_approach_data").getJSONObject(0)
            val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                .getDouble("kilometers_per_second")
            val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                .getDouble("astronomical")
            val isPotentiallyHazardous = asteroidJson
                .getBoolean("is_potentially_hazardous_asteroid")
            val asteroid = Asteroid(id, codename, formattedDate, absoluteMagnitude,
                estimatedDiameter, relativeVelocity, distanceFromEarth, isPotentiallyHazardous)
            asteroidList.add(asteroid)
        }
    }

    return asteroidList
}


fun parseImageofTheDay(jsonresult : JSONObject):ImgOfTheDay{

  val img_copyright = jsonresult.getString("copyright")
  val img_date = jsonresult.getString("date")
  val img_explanation = jsonresult.getString("explanation")
  val img_hdurl = jsonresult.getString("hdurl")
  val img_media_type = jsonresult.getString("media_type")
  val img_service_version = jsonresult.getString("service_version")
  val img_url = jsonresult.getString("url")
  val img_title = jsonresult.getString("title")

return ImgOfTheDay(img_copyright , img_date , img_explanation , img_hdurl , img_media_type , img_service_version , img_url,img_title)

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
