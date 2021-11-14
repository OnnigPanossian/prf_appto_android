package com.example.appto.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appto.adapters.RentalAdapter
import com.example.appto.databinding.FragmentRentalBinding
import com.example.appto.session.SessionManager
import com.example.appto.viewmodels.VehicleViewModel

class RentalFragment : Fragment() {

    private lateinit var binding: FragmentRentalBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRentalBinding.inflate(layoutInflater)
        sessionManager = SessionManager(context!!)

        binding.rRecycler.layoutManager = LinearLayoutManager(activity)
        val vehicleViewModel = ViewModelProvider(this)[VehicleViewModel::class.java]

        vehicleViewModel.getAllRentals("Bearer ${sessionManager.fetchAuthToken()}")
        vehicleViewModel.rentalList.observe(this, { rentalList ->
            run {
                binding.rRecycler.adapter = RentalAdapter(rentalList)
                if (rentalList.isEmpty()) {
                    binding.rEmptyView.visibility = View.VISIBLE
                } else {
                    binding.rEmptyView.visibility = View.GONE
                }
            }
        })
        return binding.root
    }
}