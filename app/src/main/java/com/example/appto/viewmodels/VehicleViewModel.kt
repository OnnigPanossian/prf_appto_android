package com.example.appto.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appto.models.Rental
import com.example.appto.models.Vehicle
import com.example.appto.services.userService
import com.example.appto.services.vehicleService
import com.example.appto.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

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

    fun returnVehicle(parkingId: String, token: String) { //TODO: Arreglar el request
        try {
            // NO ME ESTÁ HACIENDO EL REQUEST DE RETURN
            Log.d("RENTAL", "RETURN VEHICLE")
            var call: Response<Rental>
            viewModelScope.launch {
                call = userService.getRental(token)
                withContext(Dispatchers.IO) {
                    if (call.isSuccessful) {
                        Log.d("RENTAL", call.body().toString())
                    }
                }
            }
            Log.d("RENTAL", "PRE RETURN")

            var callVehicleReturn: Response<Void>
            val vehicleId = "61649340327d2fd56f4dde6a"
            viewModelScope.launch {
                callVehicleReturn = vehicleService.returnVehicle(vehicleId, parkingId)
                withContext(Dispatchers.IO) {
                    if (callVehicleReturn.isSuccessful) {
                        Log.d("RENTAL", "OK")
                    }
                }
            }
        } catch (e: Exception) {
            // Mejorar manejo de exceptions
            throw e
        }
    }
}