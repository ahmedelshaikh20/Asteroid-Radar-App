package com.udacity.asteroidradar.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
lateinit var  adapter :AsteroidsAdapter;
    private val viewModel: MainViewModel by lazy {
      val activity = requireNotNull(this.activity) {
        "You can only access the viewModel after onViewCreated()"
      }
        ViewModelProvider(this , MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
       adapter =  AsteroidsAdapter(OnClickListener {
        viewModel.DisplayAsteroidDetail(it);
      })
       binding.asteroidRecycler.adapter=adapter

      viewModel.asteroids?.observe(viewLifecycleOwner , Observer{
        adapter.submitList(it)

      })

    viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner , Observer {
      if ( null != it ) {
        this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        viewModel.DisplayAsteroidDetailCompleted(it);
      }


    })
        setHasOptionsMenu(true)

        return binding.root
    }



  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
          R.id.show_all_menu ->{
            viewModel.UpdateAsteroidsList("All")
          }
          R.id.show_rent_menu ->{
            viewModel.UpdateAsteroidsList("day")
          }
          R.id.show_buy_menu->{
            viewModel.UpdateAsteroidsList("saved")
          }
        }
      viewModel.asteroids?.observe(viewLifecycleOwner , Observer{
        adapter.submitList(it)

      })


      return true
    }}
//  private fun checkForInternet(context: Context): Boolean {
//
//    // register activity with the connectivity manager service
//    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//
//
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//      // Returns a Network object corresponding to
//      // the currently active default data network.
//      val network = connectivityManager.activeNetwork ?: return false
//
//      // Representation of the capabilities of an active network.
//      val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
//
//      return when {
//        // Indicates this network uses a Wi-Fi transport,
//        // or WiFi has network connectivity
//        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
//
//        // Indicates this network uses a Cellular transport. or
//        // Cellular has network connectivity
//        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
//
//        // else return false
//        else -> false
//      }
//    } else {
//      // if the android version is below M
//      @Suppress("DEPRECATION") val networkInfo =
//        connectivityManager.activeNetworkInfo ?: return false
//      @Suppress("DEPRECATION")
//      return networkInfo.isConnected
//    }
//  }





