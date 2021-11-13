package com.example.appto.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.appto.R
import com.example.appto.databinding.ActivityMainBinding
import com.example.appto.session.SessionManager
import com.example.appto.viewmodels.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        sessionManager = SessionManager(this)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        checkToken()

        val drawerLayout = binding.drawerLayout
        binding.imageMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navigationView = binding.navigationView
        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id)
        NavigationUI.setupWithNavController(navigationView, navHostFragment!!.findNavController())

        navigationView.menu.findItem(R.id.menuLogout).setOnMenuItemClickListener {
            userViewModel.logout("Bearer ${sessionManager.fetchAuthToken().toString()}")
            true
        }

        userViewModel.user.observe(this, { user ->
            if (user == null) {
                sessionManager.saveAuthToken(null)
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
        })

        setContentView(binding.root)
    }

    private fun checkToken() {
        val token = sessionManager.fetchAuthToken()
        if (token != null) {
            userViewModel.getUser(token)
        }
    }


}