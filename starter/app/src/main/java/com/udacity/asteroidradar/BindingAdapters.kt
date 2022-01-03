package com.udacity.asteroidradar

import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.udacity.asteroidradar.main.AsteroidsAdapter

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("imgofthedayurl")
fun bindImgoftheDay(imageView: ImageView , url :String?){
  if (!url.isNullOrEmpty()){
  url?.let {
    val imgUri = it.toUri().buildUpon().scheme("https").build()
    Glide.with(imageView.context)
      .load(imgUri)
      .into(imageView)
  }}else {
    imageView.setImageResource(R.drawable.placeholder_picture_of_day)
  }

}


@BindingAdapter("listData")
fun bindAsteroid(recyclerView: RecyclerView , data : List<Asteroid>?){
  if (!data.isNullOrEmpty()) {
    recyclerView.visibility= VISIBLE
    val adapter = recyclerView.adapter as AsteroidsAdapter
    adapter.submitList(data)
  } else
  {
recyclerView.visibility=GONE
  }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
