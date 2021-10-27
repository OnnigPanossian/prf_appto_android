package com.example.appto.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.appto.databinding.ActivityLoginBinding
import com.example.appto.viewmodels.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setRegisterRedirectEvent()

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.buttonLog.setOnClickListener {
            val userEmail = binding.inputMailLog.text.toString()
            val userPass = binding.inputPassLog.text.toString()

            val validInputs = validateInputs(userEmail, userPass)

            if (validInputs) {
                val ok: Boolean = userViewModel.login(userEmail, userPass)

                if (true) {
                    startActivity(Intent(this, MapsActivity::class.java))
                } else {
                    Log.i("Matias: ", "4")
                    Toast.makeText(this, "Datos de usuario inválidos", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun validateInputs(email: String, pass: String): Boolean {
        var isEmailValid = true
        var isPasswordValid = true
        binding.inputPassLog.error = null
        binding.inputMailLog.error = null
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isEmailValid = false;
            binding.inputMailLog.error = "Formato de email inválido"
        }

        if (pass.isEmpty()) {
            isPasswordValid = false
            binding.inputPassLog.error = "Debes ingresar la contraseña"
        }

        return isEmailValid && isPasswordValid
    }

    private fun setRegisterRedirectEvent() {
        binding.createAccountLog.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

}