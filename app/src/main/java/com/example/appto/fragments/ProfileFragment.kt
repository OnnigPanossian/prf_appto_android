package com.example.appto.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appto.databinding.FragmentProfileBinding
import com.example.appto.models.UpdateUserRequest
import com.example.appto.session.SessionManager
import com.example.appto.viewmodels.UserViewModel

class ProfileFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        sessionManager = SessionManager(context!!)

        userViewModel.getUser(sessionManager.fetchAuthToken())

        setObservers()
        onClickListener()

        return binding.root
    }

    private fun onClickListener() {
        binding.profileSave.setOnClickListener {
            val name = binding.inputName.text?.toString()
            val phone = binding.inputPhone.text?.toString()
            val image = binding.inputImage.text?.toString()
            val license = binding.inputLicense.text?.toString()

            val user = UpdateUserRequest(name, image, license, phone)
            userViewModel.updateUser(sessionManager.fetchAuthToken(), user)
        }
    }


    private fun setObservers() {
        userViewModel.user.observe(this, { user ->
            sessionManager.saveUserEmail(userViewModel.user.value!!.email.toString())
            binding.inputName.setText(user?.name)
            binding.inputPhone.setText(user?.phone)
            binding.inputImage.setText(user?.image)
            binding.inputLicense.setText(user?.license)
        })

        userViewModel.errorMessage.observe(this, {
            Toast.makeText(activity, "Ocurrió un error", Toast.LENGTH_LONG).show()
        })
    }
}