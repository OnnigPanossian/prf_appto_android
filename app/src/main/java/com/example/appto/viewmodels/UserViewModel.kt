package com.example.appto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appto.models.User
import kotlinx.coroutines.launch

// Usamos este viewModel para lo relacionado las activities del user
class UserViewModel : ViewModel() {

    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    public fun register(email: String, password: String): User {
        val userRequest = User(null, null, password, email, null, null, null, null, null)
        viewModelScope.launch {
            // asd
        }
        return userRequest // esta así para q compile, no va
    }
}