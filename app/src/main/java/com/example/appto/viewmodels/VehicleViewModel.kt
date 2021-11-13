package com.example.appto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appto.models.Vehicle
import com.example.appto.services.vehicleService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class VehicleViewModel : ViewModel() {

    private var _vehicleList = MutableLiveData<MutableList<Vehicle>>()
    val vehicleList: LiveData<MutableList<Vehicle>>
        get() = _vehicleList

    val qualiSuccess = MutableLiveData<Boolean>()

    val errorMessage = MutableLiveData<String>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }


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

    fun qualification(vehicleId: String, rating: Float) {
        var call: Response<Void>

        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            call = vehicleService.qualification(vehicleId, rating)
            withContext(Dispatchers.IO) {
                if (call.isSuccessful) {
                    qualiSuccess.postValue(true)
                } else {
                    qualiSuccess.postValue(false)
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
    }
}