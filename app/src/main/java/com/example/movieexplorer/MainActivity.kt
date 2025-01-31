package com.example.movieexplorer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.movieexplorer.fragments.FavoritesFragment
import com.example.movieexplorer.fragments.HomeFragment
import com.example.movieexplorer.fragments.ProfileFragment
import com.example.movieexplorer.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Load the default fragment (Home)
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        // Handle navigation item selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_favorites -> loadFragment(FavoritesFragment())
                R.id.nav_search -> loadFragment(SearchFragment())
                R.id.nav_profile -> loadFragment(ProfileFragment())
            }
            true
        }

        Thread.setDefaultUncaughtExceptionHandler { thread: Thread?, throwable: Throwable? ->
            Log.e("CRASH", "Uncaught Exception: ", throwable)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
