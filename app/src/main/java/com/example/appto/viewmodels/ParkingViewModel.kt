package com.example.appto.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appto.models.Filters
import com.example.appto.models.Parking
import com.example.appto.services.parkingService
import com.example.appto.utils.FiltersUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParkingViewModel : ViewModel() {

    private var _parkings = MutableLiveData<MutableList<Parking>>()
    val parkings: LiveData<MutableList<Parking>>
        get() = _parkings

    fun filterParkings(filters: Filters?) {
        viewModelScope.launch {
            val list = withContext(Dispatchers.IO) {
                val list = fetchParkings(getParamCategory(filters))
                list
            }
            _parkings.value = list
        }
    }

    private fun getParamCategory(filters: Filters?): String? {
        var paramCategory = ""
        filters?.category?.forEach { s ->
            paramCategory += FiltersUtil.mapCategory()[s] + ","
        }
        return if (paramCategory.isEmpty()) null else paramCategory
    }

    private suspend fun fetchParkings(paramCategory: String?): MutableList<Parking> {
        try {
            return parkingService.getAll(paramCategory)
        } catch (e: Exception) {
            throw e
        }
    }
}