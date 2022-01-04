package com.udacity.asteroidradar.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImgOfTheDay(
  val media_type : String,
  val url : String,
  val title:String

):Parcelable
