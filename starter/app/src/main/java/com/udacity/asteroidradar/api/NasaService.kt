package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//To Debug our Request
val interceptor = HttpLoggingInterceptor().apply {
  this.level = HttpLoggingInterceptor.Level.BODY
};
val client = OkHttpClient().newBuilder().addInterceptor(interceptor).build()
//


var retrofit = Retrofit.Builder()
  .client(client)
  .baseUrl(Constants.BASE_URL)
  .addConverterFactory(ScalarsConverterFactory.create())
  .build()

  interface NasaService {
    @GET("feed")
    suspend fun FetchAsteroids( @Query("start_date") start_date: String,@Query("end_date") end_date: String , @Query("api_key") apiKey: String ): Response<String>
  }


var  service : NasaService = retrofit.create(NasaService::class.java)


