package com.example.appto.models

import com.squareup.moshi.Json

data class UpdateUserRequest(
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "image")
    val image: String?,
    @field:Json(name = "license")
    val license: String?,
    @field:Json(name = "phone")
    val phone: String?,
)