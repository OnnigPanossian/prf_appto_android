package com.example.appto.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appto.models.Rental
import com.example.appto.models.User
import com.example.appto.services.rentalService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class RentalViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun pay(rentalId: String, token: String) {
        var call: Response<Rental>

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            call = rentalService.pay(rentalId, token)
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    Log.i("OK", "OK!")
                } else {
                    onError("Error : ${call.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
    }
}