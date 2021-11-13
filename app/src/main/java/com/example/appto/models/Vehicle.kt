package com.example.appto.models

import com.squareup.moshi.Json

data class Vehicle(
    @field:Json(name = "_id")
    val id: String,
    val brand: String?,
    val model: String?,
    val year: Int?,
    val category: Category?,
    var rating: Float,
    var timesRated: Int,
)