package com.udacity.asteroidradar.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private lateinit var DatabaseInstance : AsteroidDatabase
@Database (entities = [DatabaseAsteroid::class] , version = 1)
abstract  class AsteroidDatabase : RoomDatabase() {
  abstract val asteroidDao : AsteroidDao
}


fun getInstance(context : Context):AsteroidDatabase{
  synchronized(AsteroidDatabase::class.java){
  if(!::DatabaseInstance.isInitialized){

    DatabaseInstance =Room.databaseBuilder(context.applicationContext, AsteroidDatabase::class.java, "videos")
      .build()
  }}
  return DatabaseInstance;



}


