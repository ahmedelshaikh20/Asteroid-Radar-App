package com.udacity.asteroidradar.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImgOfTheDay(
  val copyright : String ,
  val date : String,
  val explanation :String ,
  val hdurl:String,
  val media_type : String,
  val service_version : String ,
  val url : String,
  val title:String

):Parcelable
