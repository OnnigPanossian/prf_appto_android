package com.example.appto.models

import com.squareup.moshi.Json
import java.util.Date

data class User(
    @Json(name = "_id")
    val id: String,
    val name: String,
    val password: String?,
    val email: String,
    val image: String?,
    val dateOfBirth: Date?,
    val token: String?,
    val license: License?,
    val admin: Boolean = false
) {
}