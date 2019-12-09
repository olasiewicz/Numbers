package com.example.tapptic.view

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem

import com.example.tapptic.R
import com.example.tapptic.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    val isTabletLandMode: Boolean
        get() = resources.getBoolean(R.bool.isTabletLand)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home && !resources.getBoolean(R.bool.isTabletLand)) {
            popNavigatorBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    fun popNavigatorBackStack() = navController.popBackStack()

}
