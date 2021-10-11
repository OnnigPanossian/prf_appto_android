package com.example.appto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appto.adapters.VehicleAdapter
import com.example.appto.databinding.ActivityVehicleListBinding

class VehicleList : AppCompatActivity() {

    lateinit var binding: ActivityVehicleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vlRecycler.layoutManager = LinearLayoutManager(this)

        val vehicleList = mutableListOf<Vehicle>()
        vehicleList.add(Vehicle("vehicle_id", "Porsche", "911", 2021, "Premium", 5F, 20))
        vehicleList.add(Vehicle("vehicle_id", "Porsche", "911", 2021, "Premium", 5F, 20))
        vehicleList.add(Vehicle("vehicle_id", "Porsche", "911", 2021, "Premium", 5F, 20))
        vehicleList.add(Vehicle("vehicle_id", "Porsche", "911", 2021, "Premium", 5F, 20))
        vehicleList.add(Vehicle("vehicle_id", "Porsche", "911", 2021, "Premium", 5F, 20))
        vehicleList.add(Vehicle("vehicle_id", "Porsche", "911", 2021, "Premium", 5F, 20))
        vehicleList.add(Vehicle("vehicle_id", "Porsche", "911", 2021, "Premium", 5F, 20))

        val adapter = VehicleAdapter(vehicleList)
        binding.vlRecycler.adapter = adapter

        if (vehicleList.isEmpty()) {
            binding.vlEmptyView.visibility = View.VISIBLE
        } else {
            binding.vlEmptyView.visibility = View.GONE
        }
    }
}