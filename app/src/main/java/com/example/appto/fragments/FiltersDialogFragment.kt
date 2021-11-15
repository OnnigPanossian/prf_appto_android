package com.example.appto.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.appto.databinding.FragmentFiltersDialogBinding
import com.example.appto.databinding.ParkingDialogBinding
import com.example.appto.models.Filters
import com.example.appto.session.SessionManager
import com.example.appto.viewmodels.VehicleViewModel

class FiltersDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentFiltersDialogBinding
    private lateinit var sessionManager: SessionManager

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //val parkingId = arguments?.getString("id").toString()
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            binding = FragmentFiltersDialogBinding.inflate(layoutInflater)
            //binding.tvDialog.text = arguments?.getString("name")

            val filters = Filters()

            val a = arrayOf("Categoría A", "Categoría B", "Categoría C")
            val selectedItems = mutableListOf<String>()
            builder
                .setTitle("Filtros")
                .setMultiChoiceItems(a, null
                ) { _, which, isChecked ->
                    if (isChecked) {
                        selectedItems.add(a[which])
                    } else if (selectedItems.contains(a[which])) {
                        selectedItems.remove(a[which])
                    }
                }
                .setPositiveButton("Aplicar filtros") { dialog, _ ->
                    filters.category = selectedItems
                    val action: NavDirections =
                        MapsFragmentDirections.actionMapsFragmentSelf(filters)
                    dialog?.dismiss()
                    findNavController().navigate(action)

                }
                .setNeutralButton("Remover filtros") { dialog, _ ->
                    filters.category = listOf()
                    val action: NavDirections =
                        MapsFragmentDirections.actionMapsFragmentSelf(filters)
                    dialog?.dismiss()
                    findNavController().navigate(action)
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}