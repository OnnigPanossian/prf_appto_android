package com.example.appto.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.appto.databinding.VehicleListItemBinding
import com.example.appto.models.Vehicle
import com.example.appto.viewmodels.VehicleViewModel
import com.example.appto.services.vehicleService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VehicleAdapter(private val vList: List<Vehicle>) :
    RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            VehicleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehicle = vList[position]
        holder.bind(vehicle)
    }

    override fun getItemCount(): Int {
        return vList.size
    }

    inner class ViewHolder(private val binding: VehicleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(vehicle: Vehicle) {
            binding.ratingBar.rating = vehicle.rating
            binding.tvCategory.text = vehicle.category
            (vehicle.brand + " " + vehicle.model).also { binding.tvModel.text = it }
            ("Año: " + vehicle.year.toString()).also { binding.tvYear.text = it }

            binding.btnReserve.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val res = vehicleService.book(vehicle.id)
                    if (res.isSuccessful) {
                        Log.i("OK", "OK")
                    } else {
                        Log.e("ERROR", "Error")
                    }
                }
            }

            binding.executePendingBindings()
        }
    }
}