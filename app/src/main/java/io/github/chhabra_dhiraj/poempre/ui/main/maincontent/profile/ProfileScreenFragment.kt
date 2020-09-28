package io.github.chhabra_dhiraj.poempre.ui.main.maincontent.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.chhabra_dhiraj.poempre.R
import io.github.chhabra_dhiraj.poempre.databinding.FragmentEditPoemScreenBinding
import io.github.chhabra_dhiraj.poempre.databinding.FragmentProfileScreenBinding
import io.github.chhabra_dhiraj.poempre.utils.SharedPreferencesManager

/**
 * A simple [Fragment] subclass.
 */
class ProfileScreenFragment : Fragment() {

    private var _binding: FragmentProfileScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tietEmailProfileScreenFragment.setText(SharedPreferencesManager.instance!!.email)
        binding.tietFirstNameProfileScreenFragment.setText(SharedPreferencesManager.instance!!.firstName)
        binding.tietLastNameProfileScreenFragment.setText(SharedPreferencesManager.instance!!.lastName)
    }

}
