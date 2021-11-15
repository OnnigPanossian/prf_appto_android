package com.example.appto.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appto.R
import com.example.appto.databinding.PaymentFragmentBinding
import com.example.appto.session.SessionManager
import com.example.appto.viewmodels.RentalViewModel
import com.example.appto.viewmodels.VehicleViewModel
import com.valdesekamdem.library.mdtoast.MDToast

class PaymentFragment : Fragment() {

    private lateinit var binding: PaymentFragmentBinding
    private val args: PaymentFragmentArgs by navArgs()
    private lateinit var vehicleViewModel: VehicleViewModel
    private lateinit var rentalViewModel: RentalViewModel
    private lateinit var vehicleId: String
    private lateinit var sessionManager: SessionManager
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PaymentFragmentBinding.inflate(layoutInflater)
        sessionManager = SessionManager(context!!)
        vehicleViewModel = ViewModelProvider(this)[VehicleViewModel::class.java]
        rentalViewModel = ViewModelProvider(this)[RentalViewModel::class.java]

        token = "Bearer ${sessionManager.fetchAuthToken().toString()}"

        binding.btnPay.setOnClickListener {
            vehicleViewModel.getRental(token)
        }

        setObservers()
        return binding.root
    }

    private fun setObservers() {
        vehicleViewModel.rental.observe(this, { rental ->
            if (rental != null) {
                vehicleId = rental.vehicle?.id.toString()
                vehicleViewModel.returnVehicle(vehicleId, args.parkingId)
                rentalViewModel.pay(rental.id, token)
                MDToast.makeText(context, "Pago realizado con éxito", Toast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show()
            }
        })

        vehicleViewModel.returnSuccess.observe(this, { success ->
            if (success) {
                sessionManager.saveRentalInProgress(
                    false)
                var action = PaymentFragmentDirections.actionPaymentFragmentToQualiFragment(vehicleId)
                findNavController().navigate(action)
            } else {
                MDToast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show()
            }
        })

        vehicleViewModel.errorMessage.observe(this, {
            MDToast.makeText(context, "Ocurrió un error", Toast.LENGTH_LONG, MDToast.TYPE_ERROR).show()
        })
    }

}