package com.example.appto.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appto.models.Parking
import com.example.appto.services.parkingService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParkingViewModel : ViewModel() {

    private var _parkings = MutableLiveData<MutableList<Parking>>()
    val parkings: LiveData<MutableList<Parking>>
        get() = _parkings

    init {
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO) {
                val list = fetchParkings()
                list
            }
            _parkings.value = list
        }
    }

    private suspend fun fetchParkings(): MutableList<Parking> {
        try {
            val parkings = parkingService.getAll()
            Log.i("Parking: ", parkings.toString())
            return parkings
        } catch (e: Exception) {
            // Mejorar manejo de exceptions
            throw e
        }
    }
}