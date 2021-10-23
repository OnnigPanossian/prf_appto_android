package com.example.appto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appto.models.Vehicle
import com.example.appto.services.vehicleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VehicleViewModel : ViewModel() {

    private var _vehicleList = MutableLiveData<MutableList<Vehicle>>()
    val vehicleList: LiveData<MutableList<Vehicle>>
        get() = _vehicleList

    init {
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO) {
                val list = fetchVehicles()
                list
            }
            _vehicleList.value = list
        }
    }

    private suspend fun fetchVehicles(): MutableList<Vehicle> {
        try {
            return vehicleService.getAll()
        } catch (e: Exception) {
            // Mejorar manejo de exceptions
            throw e
        }
    }
}