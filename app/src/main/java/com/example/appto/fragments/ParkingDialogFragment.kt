package com.example.appto.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.appto.R
import com.example.appto.databinding.ParkingDialogBinding

class ParkingDialogFragment(val name: String, val location: String) : DialogFragment() {

    private lateinit var binding: ParkingDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ParkingDialogBinding.inflate(layoutInflater)
        binding.etUsername.hint = name
        return inflater.inflate(R.layout.parking_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        binding.etUsername.hint = name
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}