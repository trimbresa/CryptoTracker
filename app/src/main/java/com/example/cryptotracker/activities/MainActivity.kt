package com.example.cryptotracker.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cryptotracker.R
import com.example.cryptotracker.databinding.ActivityMainBinding
import com.example.cryptotracker.fragments.DashboardFragment
import com.example.cryptotracker.fragments.FavoritesFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    private val dashboardFragment = DashboardFragment()
    private val favoritesFragment = FavoritesFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set default fragment
        replaceFragment(dashboardFragment)

        // bind bottom nav press listeners
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.ic_dashobard -> replaceFragment(dashboardFragment)
                R.id.ic_favorites -> replaceFragment(favoritesFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        if(fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}