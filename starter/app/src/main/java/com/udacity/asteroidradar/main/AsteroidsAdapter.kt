package com.udacity.asteroidradar.main

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidHolderViewBinding
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class AsteroidsAdapter : ListAdapter<Asteroid, AsteroidsAdapter.AsteroidViewHolder>(AsteroidDiffUtil()) {


  class AsteroidViewHolder(private var binding: AsteroidHolderViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(asteroid: Asteroid){
      binding.asteroid = asteroid
      binding.executePendingBindings();
    }

  }

  private class AsteroidDiffUtil : DiffUtil.ItemCallback<Asteroid>(){
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
      return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
      return oldItem.id==newItem.id
    }


  }
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): AsteroidsAdapter.AsteroidViewHolder {
    return AsteroidViewHolder(AsteroidHolderViewBinding.inflate(LayoutInflater.from(parent.context)))

  }

  override fun onBindViewHolder(holder: AsteroidsAdapter.AsteroidViewHolder, position: Int) {
    val asteroid = getItem(position)
    holder.onBind(asteroid)

  }
}
