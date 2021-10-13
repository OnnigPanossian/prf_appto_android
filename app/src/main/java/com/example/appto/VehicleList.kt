package com.example.appto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appto.adapters.VehicleAdapter
import com.example.appto.databinding.ActivityVehicleListBinding
import com.example.appto.services.vehicleService
import com.example.appto.viewmodels.VehicleViewModel

class VehicleList : AppCompatActivity() {

    private lateinit var binding: ActivityVehicleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vlRecycler.layoutManager = LinearLayoutManager(this)
        val vehicleViewModel = ViewModelProvider(this).get(VehicleViewModel::class.java)

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


    }
}