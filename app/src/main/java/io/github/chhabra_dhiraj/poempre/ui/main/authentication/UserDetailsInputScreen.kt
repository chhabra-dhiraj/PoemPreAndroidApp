package io.github.chhabra_dhiraj.poempre.ui.main.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import io.github.chhabra_dhiraj.poempre.R
import io.github.chhabra_dhiraj.poempre.databinding.FragmentEmailInputScreenBinding
import io.github.chhabra_dhiraj.poempre.databinding.FragmentUserDetailsInputScreenBinding
import io.github.chhabra_dhiraj.poempre.viewmodels.AuthenticationViewModel

/**
 * A simple [Fragment] subclass.
 */
class UserDetailsInputScreen : Fragment() {

    private var _binding: FragmentUserDetailsInputScreenBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AuthenticationViewModel
            by navGraphViewModels(R.id.authentication_navigation_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailsInputScreenBinding.inflate(layoutInflater, container, false)

        // Set the lifecycleOwner so DataBinding can observe LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnSubmitUserDetailsInputScreenFragment.setOnClickListener {
            val firstName = binding.tietFirstNameUserDetailsInputScreenFragment.text.toString()
            val lastName = binding.tietLastNameUserDetailsInputScreenFragment.text.toString()
            val password = binding.tietPasswordUserDetailsInputScreenFragment.text.toString()
            val confirmPass =
                binding.tietConfirmPasswordUserDetailsInputScreenFragment.text.toString()
            if (password == confirmPass) {
                viewModel.register(firstName, lastName, password)
            } else {
                Snackbar.make(binding.root, "Password do not match", Snackbar.LENGTH_SHORT).show()
            }
        }

        viewModel.isRegisterSuccessful.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessfull) {
                findNavController().navigate(UserDetailsInputScreenDirections.actionUserDetailsInputScreenFragmentToMainContentNavigationGraph())
            } else {
                Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
