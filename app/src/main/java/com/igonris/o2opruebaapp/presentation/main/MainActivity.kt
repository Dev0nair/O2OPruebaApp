package com.igonris.o2opruebaapp.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.igonris.o2opruebaapp.R
import com.igonris.o2opruebaapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen(true)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setupActionBarWithNavController(
            navController = navHost.navController,
            configuration = AppBarConfiguration(
                topLevelDestinationIds = setOf(
                    R.id.splashFragment,
                    R.id.homeFragment
                )
            )
        )
    }

    fun showLoading(show: Boolean) {
        if(show) {
            viewBinding.loading.show()
        } else {
            viewBinding.loading.hide()
        }
    }

    fun setFullScreen(set: Boolean) {
        if(set) {
            supportActionBar?.hide()
        } else {
            supportActionBar?.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() ||
                super.onSupportNavigateUp()
    }
}