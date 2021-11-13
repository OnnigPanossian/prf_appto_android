package com.example.appto.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appto.models.AuthRequest
import com.example.appto.models.Rental
import com.example.appto.models.User
import com.example.appto.services.userService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            call = userService.register(userRequest)
            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    _user.postValue(call.body())
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
                    _user.postValue(call.body())
                } else {
                    onError("Error : ${call.message()} ")
                }
            }
        }
    }

    fun authenticateUser(token: String) {
        var call: Response<User>

        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            call = userService.getUser(token)
            withContext(Dispatchers.IO) {
                if (call.isSuccessful) {
                    _user.postValue(call.body())
                } else {
                    onError("Error : ${call.message()} ")
                }
            }
        }
    }

    fun logout(token: String) {
        var call: Response<Void>

        viewModelScope.launch(Dispatchers.Main + exceptionHandler) {
            call = userService.logout(token)
            withContext(Dispatchers.IO) {
                if (call.isSuccessful) {
                    _user.postValue(null)
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