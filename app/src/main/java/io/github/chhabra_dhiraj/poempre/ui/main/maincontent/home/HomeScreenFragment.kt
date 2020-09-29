package io.github.chhabra_dhiraj.poempre.ui.main.maincontent.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import io.github.chhabra_dhiraj.poempre.R
import io.github.chhabra_dhiraj.poempre.databinding.FragmentHomeScreenBinding
import io.github.chhabra_dhiraj.poempre.domain.Poem
import io.github.chhabra_dhiraj.poempre.ui.main.maincontent.home.adapters.HomeScreenAdapter
import io.github.chhabra_dhiraj.poempre.viewmodels.AuthenticationViewModel
import io.github.chhabra_dhiraj.poempre.viewmodels.MainContentViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null

    private val binding get() = _binding!!

    private val mainContentViewModel: MainContentViewModel by activityViewModels()

    private val homeScreenAdapter: HomeScreenAdapter = HomeScreenAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(layoutInflater, container, false)

        binding.rvPoemHomeScreenFragment.adapter = homeScreenAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mainContentViewModel.getUserAndPoem()
        mainContentViewModel.poems.observe(viewLifecycleOwner, Observer {
            val poems = it

            if (poems == null || poems.isEmpty()) {
                binding.progressBarHomeScreenFragment.visibility = View.GONE
                binding.noPoemLayoutHomeScreenFragment.visibility = View.VISIBLE
            } else {
                // submit list to recycler view adapter
                homeScreenAdapter.submitList(poems)
                binding.noPoemLayoutHomeScreenFragment.visibility = View.GONE
                binding.progressBarHomeScreenFragment.visibility = View.GONE
                binding.rvPoemHomeScreenFragment.visibility = View.VISIBLE
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
