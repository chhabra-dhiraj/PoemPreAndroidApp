package io.github.chhabra_dhiraj.poempre.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import io.github.chhabra_dhiraj.poempre.databinding.FragmentSplashScreenBinding
import io.github.chhabra_dhiraj.poempre.utils.NetworkUtils
import io.github.chhabra_dhiraj.poempre.utils.SharedPreferencesManager
import io.github.chhabra_dhiraj.poempre.viewmodels.AuthenticationViewModel
import io.github.chhabra_dhiraj.poempre.viewmodels.MainContentViewModel

/**
 * A simple [Fragment] subclass.
 */
class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mainContentViewModel: MainContentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // route to appropriate screens based on conditions
        val sharedPreferencesManager = SharedPreferencesManager.instance
        val networkUtils = NetworkUtils.instance
        if (sharedPreferencesManager != null) {
            if (sharedPreferencesManager.sessionId != null) {
                // Check if internet connection is there or not
                // if internet connection is there
                // Call /users route
                mainContentViewModel.getUserAndPoem()
                // Check if slow internet connection issue
                // or network timeout or other server problems
                // if network timeout or other server problems
                // navigate to the home screen fragment and load data from the database there
                // with could not refresh the data error message
//                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToMainContentNavigationGraph())
                // else if session expired problem
                // Delete data in shared preferences
//                sharedPreferencesManager.clear()
                // and navigate to the authentication flow
                // with session expired so login again message
//                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToAuthenticationNavigationGraph())
                // else if everything went normal
                // navigate to the home screen fragment and load data from the database there
                // with fetched latest data message
                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToMainContentNavigationGraph())
                // else if no internet connection
                // navigate to the home screen fragment and load data from the database there
                // with could not refresh the data error message
//                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToMainContentNavigationGraph())
            } else {
                // check if internet connection is there or not
                // if internet connection is there
                // navigate to the authentication flow
                findNavController().navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToAuthenticationNavigationGraph())
                // else if no internet connection
                // stay on the splash screen and show no internet connection error message
//                binding.progressBarSplashScreenFragment.visibility = View.GONE
//                binding.imgvNoInternetConnectionSplashScreenFragment.visibility = View.VISIBLE
//                binding.tvNoInternetConnectionSplashScreenFragment.visibility = View.VISIBLE
                // else if internet returns
                // show progress bar
//                binding.imgvNoInternetConnectionSplashScreenFragment.visibility = View.GONE
//                binding.tvNoInternetConnectionSplashScreenFragment.visibility = View.GONE
//                binding.progressBarSplashScreenFragment.visibility = View.VISIBLE
            }
        } else {
            binding.progressBarSplashScreenFragment.visibility = View.GONE
            binding.imgvSharedPrefErrorSplashScreenFragment.visibility = View.VISIBLE
            binding.tvSharedPrefErrorSplashScreenFragment.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
