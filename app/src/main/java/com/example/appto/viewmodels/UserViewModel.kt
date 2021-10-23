package com.example.appto.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appto.models.User
import com.example.appto.services.userService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody

class UserViewModel : ViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun register(email: String, password: String): Boolean {
        val userRequest = User(null, null, password, email, null, null, null, null, null)
        Log.i("Matias: ", userRequest.toString())
        viewModelScope.launch {
            Log.i("Matias: ", "5")
            _user.value = withContext(Dispatchers.IO) {
                Log.i("Matias: ", "6")
                userService.register(RequestBody.create(MediaType.parse("application/json"), userRequest.toString()))
            }
        }
        Log.i("User: ", this.user.value.toString())
        return _user.value != null
    }
}