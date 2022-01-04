package com.udacity.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.udacity.asteroidradar.Worker.RefreshData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MyApplication : Application()  {
  val applicationScope = CoroutineScope(Dispatchers.Default)


  override fun onCreate() {
    super.onCreate()
    delayedinit()
  }

  private fun delayedinit() {
    applicationScope.launch {
      setUpRecurringWork()
    }
  }


  fun setUpRecurringWork(){
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.UNMETERED)
      .setRequiresBatteryNotLow(true)
      .setRequiresCharging(true)
      .apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          setRequiresDeviceIdle(true)
        }
      }.build()
    val repeatingRequest = PeriodicWorkRequestBuilder<RefreshData>(1,TimeUnit.DAYS).setConstraints(constraints).build()
    WorkManager.getInstance().enqueueUniquePeriodicWork(
      RefreshData.WORK_NAME,
      ExistingPeriodicWorkPolicy.KEEP,
      repeatingRequest
    )
  }


}
