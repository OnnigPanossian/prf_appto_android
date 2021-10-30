package com.example.appto.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appto.adapters.VehicleAdapter
import com.example.appto.databinding.FragmentVehicleListBinding
import com.example.appto.viewmodels.VehicleViewModel

class VehicleListFragment : Fragment() {

    private lateinit var binding: FragmentVehicleListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVehicleListBinding.inflate(layoutInflater)

        binding.vlRecycler.layoutManager = LinearLayoutManager(activity)
        val vehicleViewModel = ViewModelProvider(this)[VehicleViewModel::class.java]

        vehicleViewModel.vehicleList.observe(this, { vehicleList ->
            run {
                binding.vlRecycler.adapter = VehicleAdapter(vehicleList)
                if (vehicleList.isEmpty()) {
                    binding.vlEmptyView.visibility = View.VISIBLE
                } else {
                    binding.vlEmptyView.visibility = View.GONE
                }
            }
        })
        return binding.root
    }
}