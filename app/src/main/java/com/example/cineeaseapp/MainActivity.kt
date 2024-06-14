package com.example.cineeaseapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import com.example.cineeaseapp.databinding.ActivityMainBinding
import com.example.cineeaseapp.screen.FilmScreen
import com.example.cineeaseapp.screen.OrderScreen
import com.example.cineeaseapp.screen.SnackScreen

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set default fragment
        supportFragmentManager.beginTransaction().replace(R.id.main, FilmScreen()).commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            var selectedFragment: Fragment = FilmScreen()

            when (item.itemId) {
                R.id.nav_film -> selectedFragment = FilmScreen()
                R.id.nav_snack -> selectedFragment = SnackScreen()
                R.id.nav_order -> selectedFragment = OrderScreen()
            }

            supportFragmentManager.beginTransaction().replace(R.id.main, selectedFragment).commit()

            true
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(top = statusBarInsets.top)
            insets
        }
    }
}