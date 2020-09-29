package io.github.chhabra_dhiraj.poempre.ui.main.maincontent.profile

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
import io.github.chhabra_dhiraj.poempre.databinding.FragmentEditPoemScreenBinding
import io.github.chhabra_dhiraj.poempre.databinding.FragmentProfileScreenBinding
import io.github.chhabra_dhiraj.poempre.utils.SharedPreferencesManager
import io.github.chhabra_dhiraj.poempre.viewmodels.AppInsideViewModel

/**
 * A simple [Fragment] subclass.
 */
class ProfileScreenFragment : Fragment() {

    private var _binding: FragmentProfileScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: AppInsideViewModel
            by navGraphViewModels(R.id.main_content_navigation_graph)

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

        binding.btnSaveProfileScreenFragment.setOnClickListener {
            viewModel.updateUser(
                binding.tietEmailProfileScreenFragment.text.toString(),
                binding.tietFirstNameProfileScreenFragment.text.toString(),
                binding.tietLastNameProfileScreenFragment.text.toString()
            )
        }

        binding.btnDeleteProfileScreenFragment.setOnClickListener {
            viewModel.deleteUser()
        }

        viewModel.isUserUpdateSuccessful.observe(viewLifecycleOwner, Observer {
            if (it.peekContent()) {
                if (it.getContentIfNotHandled() != null) {
                    Snackbar.make(
                        binding.root,
                        "Successfully updated user details",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                }
            } else {
                if (it.getContentIfNotHandled() != null) {
                    Snackbar.make(binding.root, "Server error", Snackbar.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.isUserDeleteSuccessful.observe(viewLifecycleOwner, Observer {
            if (it.peekContent()) {
                if (it.getContentIfNotHandled() != null) {
                    Snackbar.make(
                        binding.root,
                        "Successfully deleted user details",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack(R.id.main_content_navigation_graph, true)
                    findNavController().navigate(R.id.authentication_navigation_graph)
                }
            } else {
                if (it.getContentIfNotHandled() != null) {
                    Snackbar.make(binding.root, "Server error", Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
