package com.example.appto.models

import com.squareup.moshi.Json

data class Vehicle(
    @Json(name = "_id")
    val id: String,
    val brand: String,
    val model: String,
    val year: Int,
    val category: String?,
    var rating: Float = 0F,
    var timesRated: Int = 0
)