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
import com.example.appto.databinding.FragmentLoginBinding
import com.example.appto.viewmodels.UserViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        binding.buttonLog.setOnClickListener {
            val userEmail = binding.inputMailLog.text.toString()
            val userPass = binding.inputPassLog.text.toString()

            val validInputs = validateInputs(userEmail, userPass)

            if (validInputs) {
                val ok: Boolean = userViewModel.login(userEmail, userPass)

                if (true) {

                } else {
                    Toast.makeText(activity, "Datos de usuario inválidos", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

        binding.createAccountLog.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        return binding.root
    }

    private fun validateInputs(email: String, pass: String): Boolean {
        var isEmailValid = true
        var isPasswordValid = true
        binding.passLog.error = null
        binding.emailLog.error = null
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isEmailValid = false;
            binding.emailLog.error = "Formato de email inválido"
        }

        if (pass.isEmpty()) {
            isPasswordValid = false
            binding.passLog.error = "Debes ingresar la contraseña"
        }

        return isEmailValid && isPasswordValid
    }

}