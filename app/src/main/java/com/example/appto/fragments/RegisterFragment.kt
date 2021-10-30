package com.example.appto.fragments

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.appto.R
import com.example.appto.databinding.FragmentRegisterBinding

import com.example.appto.viewmodels.UserViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.buttonReg.setOnClickListener {
            val userEmail = binding.inputMailReg.text.toString()
            val userPass = binding.inputPassReg.text.toString()

            val validInputs = validateInputs(userEmail, userPass)

            if (validInputs) {
                val ok: Boolean = userViewModel.register(userEmail, userPass)

                if (ok) {

                } else {
                    Toast.makeText(
                        activity,
                        "Ocurrió un error al crear la cuenta",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.loginReg.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)
        }

        return binding.root
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

}