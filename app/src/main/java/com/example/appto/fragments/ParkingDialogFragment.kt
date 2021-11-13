package com.example.appto.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.appto.databinding.ParkingDialogBinding
import com.example.appto.session.SessionManager
import com.example.appto.viewmodels.VehicleViewModel

class ParkingDialogFragment : DialogFragment() {

    private lateinit var binding: ParkingDialogBinding
    private lateinit var vehicleViewModel: VehicleViewModel
    private lateinit var sessionManager: SessionManager

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        vehicleViewModel = ViewModelProvider(this)[VehicleViewModel::class.java]
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        sessionManager = SessionManager(context!!)

        val rentalInProgress = sessionManager.isRentalInProgress()
        val text = getButtonText(rentalInProgress)
        val parkingId = arguments?.getString("id").toString()
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            binding = ParkingDialogBinding.inflate(layoutInflater)
            binding.tvDialog.text = arguments?.getString("name")
            binding.etAddress.hint = arguments?.getString("address")
            binding.etVehicles.text = arguments?.getString("distance")
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(binding.root)
                .setPositiveButton(text) { _, _ ->
                    if (!rentalInProgress) {
                        val action = MapsFragmentDirections.actionMapsFragmentToVehicleListFragment(
                            parkingId
                        )
                        dialog?.dismiss()
                        findNavController().navigate(action)
                    } else {
                        returnVehicle(parkingId)
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun returnVehicle(parkingId: String) {
        vehicleViewModel.getRental("Bearer ${sessionManager.fetchAuthToken().toString()}")

        vehicleViewModel.rental.observe(this, { rental ->
            if (rental != null) {
                vehicleViewModel.returnVehicle(rental.vehicle?.id.toString(), parkingId)
            }
        })

        vehicleViewModel.returnSuccess.observe(this, { success ->
            if (success) {
                sessionManager.saveRentalInProgress(false)
                val action = VehicleListFragmentDirections.actionVehicleListFragmentToQualiFragment(
                    parkingId
                )
                dialog?.dismiss()
                findNavController().navigate(action)
            } else {
                Toast.makeText(activity, "Ocurrió un error", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getButtonText(rentalInProgress: Boolean): String {
        return if (rentalInProgress) {
            "Devolver"
        } else {
            "Ver autos"
        }
    }
}