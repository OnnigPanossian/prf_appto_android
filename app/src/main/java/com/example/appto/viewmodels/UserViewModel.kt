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

class UserViewModel : ViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun register(email: String, password: String): Boolean {


        val userRequest = User(null, null, password, email, null, null, null, null, null)

        viewModelScope.launch {

            _user.value = withContext(Dispatchers.IO) {

                registerUser(userRequest)
            }

        }

        Log.i("User: ", this.user.value.toString())
        return _user.value != null
    }
    private fun registerUser (userRequest: User) : User?{
            var user : User? = null;
        try{

            user = userService.register(userRequest)
            }
            catch (err: Exception)
            {
                Log.d("error", err.message.toString() + err.localizedMessage.toString() + err.cause.toString())
            }
       return user;

    }
}