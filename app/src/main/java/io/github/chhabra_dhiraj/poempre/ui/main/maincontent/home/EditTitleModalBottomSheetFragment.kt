package io.github.chhabra_dhiraj.poempre.ui.main.maincontent.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import io.github.chhabra_dhiraj.poempre.R
import io.github.chhabra_dhiraj.poempre.databinding.FragmentEditGenreBottomModalSheetBinding
import io.github.chhabra_dhiraj.poempre.databinding.FragmentEditTitleModalBottomSheetBinding

class EditTitleModalBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEditTitleModalBottomSheetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var mListener: TitleListener? = null

    fun setListener(titleListener: TitleListener) {
        mListener = titleListener
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentEditTitleModalBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnDoneTitle.setOnClickListener {
            val title = binding.etTitleEditPoemScreenDialog.text.toString()
            mListener!!.doneTitle(title)
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    interface TitleListener {
        fun doneTitle(title: String)
    }

}
