package com.example.appto.models

import com.squareup.moshi.Json

data class User(
    @field:Json(name = "_id")
    val id: String?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "password")
    val password: String?,
    @field:Json(name = "email")
    val email: String?,
    @field:Json(name = "image")
    val image: String?,
    @field:Json(name = "dateOfBirth")
    val dateOfBirth: String?,
    @field:Json(name = "token")
    val token: String?,
    @field:Json(name = "license")
    val license: String?,
    @field:Json(name = "admin")
    val admin: Boolean?,
    @field:Json(name = "phone")
    val phone: String?,
)