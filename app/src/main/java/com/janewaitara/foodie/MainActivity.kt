package com.janewaitara.foodie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /**loading the nav graph*/
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)

        setUpNavigationDrawer()

    }

    private fun setUpNavigationDrawer() {
        // Update action bar to reflect navigation
        /**
         * ensures that the title in the action bar will automatically be updated when the
         * destination changes (assuming that android:label values are set up).
         * In addition, the Up button will be displayed when you are on a non-root destination and
         * the hamburger icon will be displayed when on the root destination.*/
        setupActionBarWithNavController(this, navController, drawerLayout)

        /**Handle nav drawer item clicks*/
        navigationDrawer.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()
            true
        }

        /**
         * ensures that the selected item in the NavigationView will
         * automatically be updated when the destination changes.*/
        setupWithNavController(navigationDrawer,navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        /**
         * ensures that the menu items in the Nav Drawer
         * stay in sync with the navigation graph.*/
        return navigateUp(navController, drawerLayout)
    }


    /**
     * ensures that the navigation drawer is close when back button is pressed*/
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
            super.onBackPressed()
    }



}