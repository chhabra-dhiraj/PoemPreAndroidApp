package io.github.chhabra_dhiraj.poempre.ui.main.maincontent.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.github.chhabra_dhiraj.poempre.R
import io.github.chhabra_dhiraj.poempre.databinding.FragmentEditGenreBottomModalSheetBinding

class EditGenreBottomModalSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEditGenreBottomModalSheetBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var mListener: ScreenListener? = null

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    fun setListener(genreListener: ScreenListener) {
        mListener = genreListener
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentEditGenreBottomModalSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnDoneGenre.setOnClickListener {
            val id = binding.chipGroupGenreEditPoemScreenDialog.checkedChipId
            if(id == R.id.myth_genre) {
                mListener!!.done("myth")
                dismiss()
            } else if (id == R.id.love_genre) {
                mListener!!.done("love")
                dismiss()
            } else {
                mListener!!.done("nature")
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    interface ScreenListener {
        fun done(genre: String)
    }
}
