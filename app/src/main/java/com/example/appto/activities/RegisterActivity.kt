package com.example.appto.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

        setLoginRedirectEvent()

        Log.i("Matias: ", "1")
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.buttonReg.setOnClickListener {
            Log.i("Matias: ", "2")
            var userEmail = binding.inputMailReg.text.toString()
            var userPass = binding.inputPassReg.text.toString()

            validateInputs()

            val ok: Boolean = userViewModel.register(userEmail, userPass)
            if(ok) {
                Log.i("Matias: ", "3")
                // todo peola
            } else {
                Log.i("Matias: ", "4")
                // mostrar toast
            }
        }
    }

    private fun validateInputs() {
        // TODO("Not yet implemented")
    }

    private fun setLoginRedirectEvent() {
        binding.loginReg.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}