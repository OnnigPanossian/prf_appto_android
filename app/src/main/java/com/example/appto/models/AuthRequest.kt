package com.example.appto.models

import com.squareup.moshi.Json

data class AuthRequest(

    @field:Json(name = "email")
    var email: String,

    @field:Json(name = "password")
    var password: String
)