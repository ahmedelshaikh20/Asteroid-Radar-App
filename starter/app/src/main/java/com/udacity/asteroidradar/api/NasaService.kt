package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//To Convert from json to kotlin object


val interceptor = HttpLoggingInterceptor().apply {
  this.level = HttpLoggingInterceptor.Level.BODY

};

val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()

private val moshi = Moshi.Builder()
  .add(KotlinJsonAdapterFactory())
  .add(AsteroidAdapter())
  .build()

var retrofit = Retrofit.Builder()
  .client(client)
  .baseUrl(Constants.BASE_URL)
  .addConverterFactory(MoshiConverterFactory.create(moshi))
  .build()

  interface NasaService {
    @GET("feed")

    suspend fun FetchAsteroids(@Query("api_key") apiKey: String): List<Asteroid>
  }


var  service : NasaService = retrofit.create(NasaService::class.java)


