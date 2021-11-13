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
import com.example.appto.viewmodels.VehicleViewModel

class QualiFragment : Fragment() {

    private lateinit var binding: FragmentQualiBinding
    private lateinit var vehicleViewModel: VehicleViewModel
    private val args: QualiFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQualiBinding.inflate(layoutInflater)

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
                Toast.makeText(context, "Gracias por utilizar AppTo", Toast.LENGTH_LONG).show()
                Handler().postDelayed({
                    binding.root.findNavController()
                        .navigate(R.id.action_qualiFragment_to_mapsFragment)
                }, 2000)
            } else {
                Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show()
            }
        })
        vehicleViewModel.errorMessage.observe(this, {
            Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG).show()
        })
    }


}