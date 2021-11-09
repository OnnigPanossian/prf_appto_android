package com.example.appto.models

import com.squareup.moshi.Json
import java.util.*

data class License(
    @Json(name = "_id")
    val id: String,
    val expireDate: Date,
    val number: String
)