package com.janewaitara.foodie

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val END_SCALE = 0.7f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /**loading the nav graph*/
        navController = Navigation.findNavController(this,R.id.nav_host_fragment)

        setUpNavigationDrawer()
        setUpAnimation()

    }

    private fun setUpAnimation() {
        drawerLayout.setScrimColor(Color.TRANSPARENT)
        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener(){
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                /*super.onDrawerSlide(drawerView, slideOffset)*/

                // Scale the View based on current slide offset
                val diffScaledOffset = slideOffset * (1 - END_SCALE)
                val offSetScale = 1 - diffScaledOffset
                content_view.scaleX = offSetScale
                content_view.scaleY = offSetScale

                // Translate the View, accounting for the scaled width
                val  xOffset = drawerView.width * slideOffset
                val  xOffsetDiff = content_view.width * diffScaledOffset / 2
                val  xTranslation = xOffset - xOffsetDiff
                content_view.translationX = xTranslation
/*
                content_view.x = navigationDrawer.width * slideOffset
                val lp = content_view.layoutParams as RelativeLayout.LayoutParams
                lp.height = drawerView.height - (drawerView.height * slideOffset * 0.3f).toInt()
                lp.topMargin = (drawerView.height - lp.height) /2
                content_view.layoutParams = lp as ViewGroup.LayoutParams
*/
            }
        })
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