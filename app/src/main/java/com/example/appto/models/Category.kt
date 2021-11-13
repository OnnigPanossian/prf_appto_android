package com.example.appto.models

import com.squareup.moshi.Json

data class Category(
    @field:Json(name = "_id") val id: String,
    val code: String?,
    val trunkCapacity: Int?,
    val capacity: Int?,
    val costPerMinute: Int?,
    val doors: Int?,
)