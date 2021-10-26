package com.example.appto.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appto.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setRegisterRedirectEvent()
    }

    private fun setRegisterRedirectEvent() {
        binding.createAccountLog.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}