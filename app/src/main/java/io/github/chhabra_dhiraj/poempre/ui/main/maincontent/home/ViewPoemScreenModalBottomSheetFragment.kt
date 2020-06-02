package io.github.chhabra_dhiraj.poempre.ui.main.maincontent.home

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.github.chhabra_dhiraj.poempre.R
import io.github.chhabra_dhiraj.poempre.databinding.FragmentViewPoemScreenModalBottomSheetBinding

class ViewPoemScreenModalBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentViewPoemScreenModalBottomSheetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewPoemScreenModalBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

}
