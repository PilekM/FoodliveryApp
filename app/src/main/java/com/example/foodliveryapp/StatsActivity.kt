package com.example.foodliveryapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.foodliveryapp.ui.DrawerLayoutBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView


class StatsActivity : AppCompatActivity() {

    lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val drawerLayoutBuilder = DrawerLayoutBuilder(this)
        drawerLayoutBuilder.build(R.id.stats_drawer_layout, R.id.stats_nav_view, R.id.stats_drawer_item)

        bottomNavigation = findViewById(R.id.stats_bottom_nav_view)

       val navItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {

           when(it.itemId){
               R.id.daily_bottom_nav_item -> {
                   findViewById<TextView>(R.id.stats_title_tv).visibility = View.INVISIBLE
                   openFragment(DailyStatsFragment.newInstance("",""))
                   true
               }

               R.id.monthly_bottom_nav_item -> {
                   findViewById<TextView>(R.id.stats_title_tv).visibility = View.INVISIBLE
                   openFragment(MonthlyStatsFragment.newInstance("", ""))
                   true
               }

               else -> {
                    false
               }
           }
       }
        bottomNavigation.setOnNavigationItemSelectedListener(navItemSelectedListener)
        bottomNavigation.itemIconTintList = null
    }

    fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.stats_frame_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
