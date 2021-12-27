package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Asteroid

data class ResultClass(
  val near_earth_objects :List<Asteroid>
)
