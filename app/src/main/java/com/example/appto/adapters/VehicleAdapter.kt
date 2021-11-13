package com.example.appto.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appto.R
import com.example.appto.databinding.VehicleListItemBinding
import com.example.appto.models.Vehicle
import com.example.appto.services.vehicleService
import com.example.appto.session.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VehicleAdapter(private val vList: List<Vehicle>) :
    RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            VehicleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehicle = vList[position]
        holder.bind(vehicle)
    }

    override fun getItemCount(): Int = vList.size

    inner class ViewHolder(private val binding: VehicleListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val sessionManager = SessionManager(binding.root.context)

        fun bind(vehicle: Vehicle) {
            setCategoryImage(vehicle.category?.code)
            binding.ratingBar.rating = vehicle.rating
            binding.capacity.text = vehicle.category?.capacity.toString()
            binding.doors.text = vehicle.category?.doors.toString()
            binding.trunk.text = vehicle.category?.trunkCapacity.toString()

            ("Precio: " + vehicle.category?.costPerMinute.toString()).also {
                binding.price.text = it
            }
            ("Categoría " + vehicle.category?.code?.uppercase()).also {
                binding.tvCategory.text = it
            }
            (vehicle.brand + " " + vehicle.model).also {
                binding.tvModel.text = it
            }
            ("Año: " + vehicle.year.toString()).also {
                binding.tvYear.text = it
            }


            binding.btnReserve.setOnClickListener { view ->
                CoroutineScope(Dispatchers.IO).launch {
                    val res = vehicleService.book(sessionManager.fetchAuthToken(), vehicle.id)
                    withContext(Dispatchers.Main) {
                        if (res.isSuccessful) {
                            Log.i("OK", "OK")
                            sessionManager.saveRentalInProgress(true)
                            view.findNavController()
                                .navigate(R.id.action_vehicleListFragment_to_mapsFragment)
                        } else {
                            Log.e("ERROR", "Error")
                        }
                    }
                }
            }

            binding.executePendingBindings()
        }

        private fun setCategoryImage(code: String?) {
            when (code?.uppercase()) {
                "A" -> {
                    binding.carImage.setImageResource(R.drawable.cat_a)
                }
                "B" -> {
                    binding.carImage.setImageResource(R.drawable.suv)
                }
                "C" -> {
                    binding.carImage.setImageResource(R.drawable.deportivo)
                }
                else -> binding.carImage.setImageResource(R.drawable.cat_a)
            }
        }
    }
}