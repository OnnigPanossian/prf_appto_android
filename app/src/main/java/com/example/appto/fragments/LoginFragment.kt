package com.example.appto.fragments

import android.content.Intent
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
import com.example.appto.activities.MainActivity
import com.example.appto.databinding.FragmentLoginBinding
import com.example.appto.session.SessionManager
import com.example.appto.viewmodels.UserViewModel
import com.valdesekamdem.library.mdtoast.MDToast

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        sessionManager = SessionManager(context!!)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        setObservers()

        binding.createAccountLog.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.buttonLog.setOnClickListener {
            val email = binding.inputMailLog.text.toString()
            val password = binding.inputPassLog.text.toString()

            val validInputs = validateInputs(email, password)

            if (validInputs) {
                userViewModel.login(email, password)
            }
        }

        return binding.root
    }

    private fun setObservers() {
        userViewModel.user.observe(this, { user ->
            if (user != null) {
                sessionManager.saveAuthToken(userViewModel.user.value!!.token.toString())
                MDToast.makeText(context, "Inicio de sesión correcto", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        })

        userViewModel.errorMessage.observe(this, {
            MDToast.makeText(context, "Credenciales inválidas", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR).show()
        })
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