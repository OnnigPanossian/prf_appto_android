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


    fun getVehiclesByParkingId(id: String) {
        try {
            viewModelScope.launch {
                val list = withContext(Dispatchers.IO) {
                    val list = vehicleService.getVehiclesByParkingId(id)
                    list
                }
                _vehicleList.value = list
            }
        } catch (e: Exception) {
            // Mejorar manejo de exceptions
            throw e
        }
    }
}