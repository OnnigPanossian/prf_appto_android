package com.example.appto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appto.databinding.FragmentRentalItemBinding
import com.example.appto.models.Rental

class RentalAdapter(private val rList: List<Rental>) :
    RecyclerView.Adapter<RentalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentRentalItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rental = rList[position]
        holder.bind(rental)
    }

    override fun getItemCount(): Int = rList.size

    inner class ViewHolder(private val binding: FragmentRentalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(rental: Rental) {
            binding.parkingReturn.text = rental.parkingDestination?.name.toString()
            binding.parkingWithdrawal.text = rental.parkingOrigin?.name.toString()
            "$ ${rental.finalPrice.toString()}".also { binding.price.text = it }
            binding.tvBrand.text = rental.vehicle?.brand.toString()
            binding.tvModel.text = rental.vehicle?.model.toString()

            binding.executePendingBindings()
        }
    }

}