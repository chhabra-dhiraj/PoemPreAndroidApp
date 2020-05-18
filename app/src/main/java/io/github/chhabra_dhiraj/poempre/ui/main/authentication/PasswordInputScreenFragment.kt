package io.github.chhabra_dhiraj.poempre.ui.main.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.chhabra_dhiraj.poempre.R

/**
 * A simple [Fragment] subclass.
 */
class PasswordInputScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_password_input_screen, container, false)
    }

}
