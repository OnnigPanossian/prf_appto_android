package com.example.appto.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appto.adapters.VehicleAdapter
import com.example.appto.databinding.ActivityVehicleListBinding
import com.example.appto.viewmodels.VehicleViewModel

class VehicleListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVehicleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vlRecycler.layoutManager = LinearLayoutManager(this)
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
    }
}