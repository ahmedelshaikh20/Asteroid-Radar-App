package com.udacity.asteroidradar.Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

//Data Entity for Asteroids
@Entity
data class DatabaseAsteroid(
  @PrimaryKey
  val id: Long,
  @ColumnInfo(name = "code_name")
  val codename: String,
  @ColumnInfo(name = "closeApproachDate")
  val closeApproachDate: String,
  @ColumnInfo(name = "absoluteMagnitude")
  val absoluteMagnitude: Double,
  @ColumnInfo(name = "estimatedDiameter")
  val estimatedDiameter: Double,
  @ColumnInfo(name = "relativeVelocity")
  val relativeVelocity: Double,
  @ColumnInfo(name = "distanceFromEarth")
  val distanceFromEarth: Double,
  @ColumnInfo(name = "isPotentiallyHazardous")
  val isPotentiallyHazardous: Boolean
)



fun List<DatabaseAsteroid>.asDomainModel():List<Asteroid>{
  return  map{
    Asteroid(
      id = it.id,
      codename = it.codename,
      closeApproachDate = it.closeApproachDate,
      absoluteMagnitude = it.absoluteMagnitude,
      estimatedDiameter = it.estimatedDiameter,
      relativeVelocity = it.relativeVelocity,
      distanceFromEarth = it.distanceFromEarth,
      isPotentiallyHazardous = it.isPotentiallyHazardous
    )
  }

}
