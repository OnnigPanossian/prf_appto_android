package com.example.appto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.appto.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout = binding.drawerLayout
        binding.imageMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navigationView = binding.navigationView
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id)
        NavigationUI.setupWithNavController(navigationView, navHostFragment!!.findNavController())

        // Si ponemos iconos .png de colores
        // navigationView.itemIconTintList = null

    }



}