package com.udacity.asteroidradar.api

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.udacity.asteroidradar.Asteroid
import org.json.JSONObject

class AsteroidAdapter {

  @FromJson
  fun fromJson(json: String): List<Asteroid> {
    val jsonObject = JSONObject(json)
    return parseAsteroidsJsonResult(jsonObject)
  }
}
