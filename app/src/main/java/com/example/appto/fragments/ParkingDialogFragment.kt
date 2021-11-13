package com.example.appto.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.appto.databinding.ParkingDialogBinding

class ParkingDialogFragment : DialogFragment() {

    private lateinit var binding: ParkingDialogBinding

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            binding = ParkingDialogBinding.inflate(layoutInflater)
            binding.tvDialog.text = arguments?.getString("name")
            binding.etAddress.hint = arguments?.getString("address")
            binding.etVehicles.text = arguments?.getString("distance")
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(binding.root)
                .setPositiveButton("Ver autos") { _, _ ->
                    val action = MapsFragmentDirections.actionMapsFragmentToVehicleListFragment(
                        arguments?.getString("id").toString()
                    )
                    dialog!!.dismiss()
                    findNavController().navigate(action)
                    if(false) {
                        val action =
                            VehicleListFragmentDirections.actionVehicleListFragmentToQualiFragment(
                                vehicle.id
                            )
                        view.findNavController().navigate(action)
                    }
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}