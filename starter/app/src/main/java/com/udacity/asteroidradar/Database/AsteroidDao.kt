package com.udacity.asteroidradar.Database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {
@Query("SELECT * From DatabaseAsteroid")
fun getAll():LiveData<List<DatabaseAsteroid>>
@Query("SELECT * From DatabaseAsteroid a Where  a.closeApproachDate Like :date")
fun getAsteroidByDates(date :String?):LiveData<List<DatabaseAsteroid>>
@Insert
fun insertAsteroid(databaseAsteroid: DatabaseAsteroid)
@Query("DELETE  FROM DatabaseAsteroid")
fun clearDatabase()
@Insert(onConflict = OnConflictStrategy.REPLACE)
fun insertAll(vararg databaseAsteroids:DatabaseAsteroid)
}




