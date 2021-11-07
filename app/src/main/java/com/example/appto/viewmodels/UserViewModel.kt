package com.example.appto.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appto.models.AuthRequest
import com.example.appto.models.User
import com.example.appto.services.userService
import kotlinx.coroutines.*
import retrofit2.Response

class UserViewModel : ViewModel() {

    private var _user = MutableLiveData<User?>()
    val user: MutableLiveData<User?>
        get() = _user

    val errorMessage = MutableLiveData<String>()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun register(email: String, password: String) {
        val userRequest = AuthRequest(email, password)
        var call: Response<User>

        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            call = userService.register(userRequest)
            withContext(Dispatchers.IO) {
                if (call.isSuccessful) {
                    _user.value = call.body()
                } else {
                    onError("Error : ${call.message()} ")
                }
            }
        }
    }

    fun login(email: String, password: String) {
        val userRequest = AuthRequest(email, password)
        var call: Response<User>

        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            call = userService.login(userRequest)
            withContext(Dispatchers.IO) {
                if (call.isSuccessful) {
                    _user.value = call.body()
                } else {
                    onError("Error : ${call.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }
}