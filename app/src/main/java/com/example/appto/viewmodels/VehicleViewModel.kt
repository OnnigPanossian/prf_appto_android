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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class VehicleViewModel : ViewModel() {

    private var _vehicleList = MutableLiveData<MutableList<Vehicle>>()
    val vehicleList: LiveData<MutableList<Vehicle>>
        get() = _vehicleList

    private var _rental = MutableLiveData<Rental>()
    val rental: LiveData<Rental>
        get() = _rental

    val qualiSuccess = MutableLiveData<Boolean>()

    val returnSuccess = MutableLiveData<Boolean>()

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
                _vehicleList.postValue(list)
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

    fun book(token: String?, vehicleId: String) {
        var call: Response<Void>

        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            call = vehicleService.book(token, vehicleId)
            withContext(Dispatchers.IO) {
                if (call.isSuccessful) {
                    qualiSuccess.postValue(true)
                } else {
                    qualiSuccess.postValue(false)
                }
            }
        }
    }

    fun getRental(token: String?) {
        var call: Response<Rental>
        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            Log.i("POR ACA", token.toString())
            call = userService.getRental(token)
            withContext(Dispatchers.IO) {
                if (call.isSuccessful) {
                    _rental.postValue(call.body())
                } else {
                    Log.i("RENTAL", "RENTAL")
                }
            }
        }
    }

    fun returnVehicle(vehicleId: String, parkingId: String) {
        var call: Response<Void>
        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            call = vehicleService.returnVehicle(vehicleId, parkingId)
            withContext(Dispatchers.IO) {
                if (call.isSuccessful) {
                    returnSuccess.postValue(true)
                } else {
                    returnSuccess.postValue(false)
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
    }
}