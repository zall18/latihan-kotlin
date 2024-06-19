package com.example.latihan3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNav : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bottom_nav)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bottomNav = findViewById(R.id.bottomNavigation)

        bottomNav.setOnItemSelectedListener {
            menuItem ->
            when(menuItem.itemId){
                R.id.home -> {
                    replaceFragment(Home())
                    true
                }
                R.id.search -> {
                    replaceFragment(Search())
                    true
                }

                R.id.notif -> {
                    replaceFragment(Notifications())
                    true
                }
                R.id.profile -> {
                    replaceFragment(Profile())
                    true
                }


                else -> false
            }
        }

        replaceFragment(Home())
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}