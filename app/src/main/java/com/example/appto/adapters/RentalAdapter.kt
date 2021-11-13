package com.example.appto.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appto.databinding.FragmentRentalBinding
import com.example.appto.models.Rental

class RentalAdapter(private val rList: List<Rental>) :
    RecyclerView.Adapter<RentalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentRentalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rental = rList[position]
        holder.bind(rental)
    }

    override fun getItemCount(): Int = rList.size

    inner class ViewHolder(binding: FragmentRentalBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rental: Rental) {

        }
    }

}