package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidHolderViewBinding

class AsteroidsAdapter(val onClickListener: OnClickListener) : ListAdapter<Asteroid, AsteroidsAdapter.AsteroidViewHolder>(AsteroidDiffUtil()) {


  class AsteroidViewHolder(private var binding: AsteroidHolderViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(asteroid: Asteroid?){
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
    return AsteroidViewHolder(AsteroidHolderViewBinding.inflate(LayoutInflater.from(parent.context) , parent, false))

  }

  override fun onBindViewHolder(holder: AsteroidsAdapter.AsteroidViewHolder, position: Int) {
    val asteroid = getItem(position)
    holder.onBind(asteroid)
    holder.itemView.setOnClickListener{
onClickListener.onClick(asteroid)
    }

  }
}

class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
  fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}
