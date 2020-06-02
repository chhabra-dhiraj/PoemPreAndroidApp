package io.github.chhabra_dhiraj.poempre.ui.main.maincontent.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.google.android.material.snackbar.Snackbar
import io.github.chhabra_dhiraj.poempre.R
import io.github.chhabra_dhiraj.poempre.databinding.FragmentViewPoemScreenBinding
import io.github.chhabra_dhiraj.poempre.viewmodels.AppInsideViewModel
import io.github.chhabra_dhiraj.poempre.viewmodels.AuthenticationViewModel

class ViewPoemScreenFragment : Fragment() {

    private var _binding: FragmentViewPoemScreenBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: ViewPoemScreenFragmentArgs by navArgs()

    private val viewModel: AppInsideViewModel
            by navGraphViewModels(R.id.main_content_navigation_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentViewPoemScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.titleViewPoemScreenFragment.text = args.poem.title
        binding.genreViewPoemScreenFragment.text = args.poem.genre
        binding.bodyViewPoemScreenFragment.text = args.poem.body

        binding.toolbarViewPoemScreenFragment.setNavigationOnClickListener {
            val viewPoemBottomModalSheetFragment = ViewPoemScreenModalBottomSheetFragment()
            viewPoemBottomModalSheetFragment.show(
                childFragmentManager,
                viewPoemBottomModalSheetFragment.tag
            )
        }

        binding.toolbarViewPoemScreenFragment.setOnMenuItemClickListener {
            if (it.itemId == R.id.editPoemScreenFragment) {
                findNavController().navigate(
                    ViewPoemScreenFragmentDirections.actionViewPoemFragmentToEditPoemScreenFragment(
                        args.poem,
                        false
                    )
                )
            } else {
                viewModel.deletePoem(args.poem.poetryId)
            }
            return@setOnMenuItemClickListener true
        }

        viewModel.isDeleteSuccessful.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().popBackStack()
            } else {
                Snackbar.make(binding.root, "Server error", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
