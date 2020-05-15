package io.github.chhabra_dhiraj.poempre.ui.navigationui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.chhabra_dhiraj.poempre.R

class MainContentContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
