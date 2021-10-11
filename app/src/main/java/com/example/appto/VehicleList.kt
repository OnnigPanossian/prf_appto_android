package com.example.appto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appto.databinding.ActivityVehicleListBinding

class VehicleList : AppCompatActivity() {

    lateinit var binding: ActivityVehicleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVehicleListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}