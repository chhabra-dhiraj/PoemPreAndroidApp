package io.github.chhabra_dhiraj.poempre.ui.navigationui

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavArgument
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import io.github.chhabra_dhiraj.poempre.R
import io.github.chhabra_dhiraj.poempre.androidsysteminteraction.broadcastreceivers.NetworkReceiver
import io.github.chhabra_dhiraj.poempre.databinding.ActivityMainBinding
import io.github.chhabra_dhiraj.poempre.domain.Poem
import io.github.chhabra_dhiraj.poempre.viewmodels.MainContentViewModel

class MainContentContainerActivity : AppCompatActivity() {

    private val mainContentViewModel: MainContentViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private var isActivityOpenedFirstTime = true

    // The BroadcastReceiver that tracks network connectivity changes.
    private lateinit var receiver: NetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        navController = findNavController(R.id.top_level_main_content)

//        if (savedInstanceState == null) {
        setupBottomNavigationBar()
//        } // Else, need to wait for onRestoreInstanceState

        // Registers BroadcastReceiver to track network connection changes.
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        receiver = NetworkReceiver()
        this.registerReceiver(receiver, filter)
    }

//    override fun onStart() {
//        super.onStart()
//
//        val snackBar = Snackbar.make(binding.root, "Back online", Snackbar.LENGTH_INDEFINITE)
//        if (NetworkUtils.isNetworkConnected) {
//            if (isActivityOpenedFirstTime) {
//                isActivityOpenedFirstTime = false
//            } else {
//                snackBar.setText("Back online")
//                snackBar.show()
//            }
//        } else {
//            snackBar.dismiss()
//            if (navController.currentDestination!!.id != R.id.splashScreenFragment) {
//                snackBar.setText("No internet connection")
//                snackBar.show()
//            }
//        }
//
//    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        val appBarLayout = binding.appBarLayoutMainContentContainerActivity
        val layout = binding.collapsingToolbarLayoutMainContentContainerActivity
        val toolbar = binding.toolbarMainContentContainerActivity
        val bottomNavMainContent = binding.bottomNavMainContent
        val navController = findNavController(R.id.top_level_main_content)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeScreenFragment,
                R.id.editPoemScreenFragment,
                R.id.profileScreenFragment
            )
        )
        layout.setupWithNavController(toolbar, navController, appBarConfiguration)
        bottomNavMainContent.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id != R.id.homeScreenFragment &&
                destination.id != R.id.profileScreenFragment
            ) {
                appBarLayout.visibility = View.GONE
                bottomNavMainContent.visibility = View.GONE
            } else {
                if (destination.id == R.id.editPoemScreenFragment) {
                    val poem = Poem(-3, "hbbhdb", "dsgh", "dsbh")
                    val poemArg = NavArgument.Builder().setDefaultValue(poem).build()
                    val plusArg = NavArgument.Builder().setDefaultValue(true).build()
                    destination.addArgument("poem", poemArg)
                    destination.addArgument("plus", plusArg)
                }
                appBarLayout.visibility = View.VISIBLE
                bottomNavMainContent.visibility = View.VISIBLE
            }
        }

        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.aboutUsScreenFragment) {
                navController.navigate(R.id.aboutUsScreenFragment)
            } else {
                // Call logout API
                mainContentViewModel.logout()
            }
            return@setOnMenuItemClickListener true
        }

        mainContentViewModel.isLogoutSuccessful.observe(this, Observer {
            if (it) {
                // logout of the app
                navController.popBackStack(R.id.main_content_navigation_graph, true)
                navController.navigate(R.id.authentication_navigation_graph)
            } else {
                Snackbar.make(binding.root, "Server Error", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        isActivityOpenedFirstTime = true
    }

    public override fun onDestroy() {
        super.onDestroy()
        // Unregisters BroadcastReceiver when app is destroyed.
        this.unregisterReceiver(receiver)
    }
}
