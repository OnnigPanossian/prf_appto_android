package com.example.appto.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.appto.databinding.ActivityRegisterBinding
import com.example.appto.viewmodels.UserViewModel


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setLoginRedirectListener()

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.buttonReg.setOnClickListener {
            val userEmail = binding.inputMailReg.text.toString()
            val userPass = binding.inputPassReg.text.toString()

            val validInputs = validateInputs(userEmail, userPass)

            if (validInputs) {
                val ok: Boolean = userViewModel.register(userEmail, userPass)

                if (ok) {
                    Log.i("Matias: ", "3")
                    // todo peola
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    Log.i("Matias: ", "4")
                    Toast.makeText(this, "Ocurrió un error al crear la cuenta", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun validateInputs(email: String, pass: String): Boolean {
        var isEmailValid = true
        var isPasswordValid = true
        binding.emailReg.error = null
        binding.passReg.error = null
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isEmailValid = false;
            binding.emailReg.error = "Formato de email inválido"
        }

        if (pass.isEmpty() || pass.length < 8) {
            isPasswordValid = false
            binding.passReg.error = "La contraseña debe contener al menos 8 caracteres"
        }

        return isEmailValid && isPasswordValid
    }

    private fun setLoginRedirectListener() {
        binding.loginReg.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}