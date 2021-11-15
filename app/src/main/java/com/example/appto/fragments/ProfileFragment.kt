package com.example.appto.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.appto.R
import com.example.appto.databinding.ActivityMainBinding
import com.example.appto.databinding.FragmentProfileBinding
import com.example.appto.models.UpdateUserRequest
import com.example.appto.session.SessionManager
import com.example.appto.viewmodels.UserViewModel
import com.google.android.material.navigation.NavigationView
import com.valdesekamdem.library.mdtoast.MDToast

class ProfileFragment : Fragment() {

    private lateinit var sessionManager: SessionManager
    private lateinit var binding: FragmentProfileBinding
    private lateinit var navigationView: NavigationView
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        navigationView = ActivityMainBinding.inflate(layoutInflater).navigationView
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
            MDToast.makeText(
                context,
                "Perfil actualizado correctamente",
                Toast.LENGTH_SHORT,
                MDToast.TYPE_SUCCESS
            ).show()
        }
    }


    private fun setObservers() {
        userViewModel.user.observe(this, { user ->
            sessionManager.saveUserEmail(userViewModel.user.value!!.email.toString())
            binding.txtEmail.text = user?.email
            binding.inputName.setText(user?.name)
            binding.inputPhone.setText(user?.phone)
            binding.inputImage.setText(user?.image)
            binding.inputLicense.setText(user?.license)
            sessionManager.saveUserImage(user?.image)
            sessionManager.saveUserName(user?.name)
            Glide.with(this).load(user?.image).into(binding.profileImage)

            if (sessionManager.fetchUserImage() != null) {
                Glide.with(this).load(sessionManager.fetchUserImage().toString()).into(
                    navigationView.getHeaderView(0).findViewById(R.id.imageProfile)
                )
            }

            if (sessionManager.fetchUserName() != null) {
                navigationView.getHeaderView(0).findViewById<TextView>(R.id.userName).text =
                    "¡Hola " + sessionManager.fetchUserName().toString() + "!"
            }
        })

        userViewModel.errorMessage.observe(this, {
            MDToast.makeText(context, "Ocurrió un error", Toast.LENGTH_SHORT, MDToast.TYPE_ERROR)
                .show()
        })
    }
}