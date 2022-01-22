package com.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.todo.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
            .navController
            .addOnDestinationChangedListener { controller, destination, arguments ->
                if (destination.id == R.id.loginFragment) {
                    supportActionBar?.hide()
                } else {
                    supportActionBar?.show()
                }
            }
    }
}