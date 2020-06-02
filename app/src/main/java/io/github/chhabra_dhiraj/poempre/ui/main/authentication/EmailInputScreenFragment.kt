package io.github.chhabra_dhiraj.poempre.ui.main.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import io.github.chhabra_dhiraj.poempre.R
import io.github.chhabra_dhiraj.poempre.databinding.FragmentEmailInputScreenBinding
import io.github.chhabra_dhiraj.poempre.viewmodels.AuthenticationViewModel

/**
 * A simple [Fragment] subclass.
 */
class EmailInputScreenFragment : Fragment() {

    private var _binding: FragmentEmailInputScreenBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AuthenticationViewModel
            by navGraphViewModels(R.id.authentication_navigation_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmailInputScreenBinding.inflate(layoutInflater, container, false)

        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnNextEmailInputScreenFragment.setOnClickListener {
            val email = binding.tietEmailEmailInputScreenFragment.text.toString()
            viewModel.checkEmailPresent(email)
        }

        viewModel.isEmailPresent.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(EmailInputScreenFragmentDirections.actionEmailInputScreenFragmentToPasswordInputScreen())
            } else {
                findNavController().navigate(EmailInputScreenFragmentDirections.actionEmailInputScreenFragmentToUserDetailsInputScreen())
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
