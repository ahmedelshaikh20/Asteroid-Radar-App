package com.udacity.asteroidradar.Worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Database.AsteroidRepositry
import com.udacity.asteroidradar.Database.getInstance
import retrofit2.HttpException

class RefreshData(appContext: Context , params:WorkerParameters):CoroutineWorker(appContext , params) {
companion object{
  const val WORK_NAME  = "RefreshData"
}
  override suspend fun doWork(): Result {

    val database = getInstance(applicationContext)
    val repository = AsteroidRepositry(database)
    return  try {
      repository.getAsteroids(forceNetwork = true)
      Result.success()
    }catch (e: HttpException){
      Result.retry()
    }

  }
}
