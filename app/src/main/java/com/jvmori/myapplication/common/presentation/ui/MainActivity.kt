package com.jvmori.myapplication.common.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.jvmori.myapplication.R

class MainActivity : AppCompatActivity() {

    lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setupNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        Navigation.findNavController(this, R.id.navHostFragment).navigateUp()
        return super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        controller = Navigation.findNavController(this, R.id.navHostFragment)
        val appBarConfiguration =
            AppBarConfiguration.Builder(
                mutableSetOf(
                    R.id.photosFragment,
                    R.id.collectionsFragment
                )
            ).build()
        NavigationUI.setupActionBarWithNavController(this, controller, appBarConfiguration)
    }
}
