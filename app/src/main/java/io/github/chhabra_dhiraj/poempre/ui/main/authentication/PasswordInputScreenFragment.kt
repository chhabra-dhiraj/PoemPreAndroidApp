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
import io.github.chhabra_dhiraj.poempre.databinding.FragmentPasswordInputScreenBinding
import io.github.chhabra_dhiraj.poempre.viewmodels.AuthenticationViewModel

/**
 * A simple [Fragment] subclass.
 */
class PasswordInputScreenFragment : Fragment() {

    private var _binding: FragmentPasswordInputScreenBinding? = null

    private val binding get() = _binding!!

    private val viewModel: AuthenticationViewModel
            by navGraphViewModels(R.id.authentication_navigation_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPasswordInputScreenBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnSubmitPasswordPasswordInputScreenFragment.setOnClickListener {
            val password = binding.tietPasswordPasswordInputScreenFragment.text.toString()
            viewModel.login(password)
        }

        viewModel.isLoginSuccessful.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(PasswordInputScreenFragmentDirections.actionPasswordInputScreenToMainContentNavigationGraph())
            } else {
                Snackbar.make(binding.root, "Incorrect Password", Snackbar.LENGTH_SHORT).show()
            }
        })

        binding.btnForgotPasswordPasswordInputScreenFragment.setOnClickListener {
            // Call to server to send the OTP on the email
            findNavController().navigate(PasswordInputScreenFragmentDirections.actionPasswordInputScreenToOtpInputScreenFragment())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
