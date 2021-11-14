package com.example.appto.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appto.R
import com.example.appto.databinding.FragmentQualiBinding
import com.example.appto.session.SessionManager
import com.example.appto.viewmodels.VehicleViewModel
import com.valdesekamdem.library.mdtoast.MDToast

class QualiFragment : Fragment() {

    private lateinit var binding: FragmentQualiBinding
    private lateinit var vehicleViewModel: VehicleViewModel
    private lateinit var sessionManager: SessionManager
    private val args: QualiFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQualiBinding.inflate(layoutInflater)
        sessionManager = SessionManager(context!!)
        vehicleViewModel = ViewModelProvider(this)[VehicleViewModel::class.java]

        binding.btnQuali.setOnClickListener {
            vehicleViewModel.qualification(args.vehicleId, binding.ratingBar.rating)
        }

        setObervers()
        return binding.root
    }

    private fun setObervers() {
        vehicleViewModel.qualiSuccess.observe(this, { success ->
            if (success) {
                MDToast.makeText(context, "Gracias por utilizar AppTo", Toast.LENGTH_LONG, MDToast.TYPE_INFO).show()
                Handler().postDelayed({
                    binding.root.findNavController()
                        .navigate(R.id.action_qualiFragment_to_mapsFragment)
                }, 2000)
            } else {
                MDToast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show()
            }
        })

        vehicleViewModel.errorMessage.observe(this, {
            MDToast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show()
        })
    }
}