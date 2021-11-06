package com.example.appto.models

import com.squareup.moshi.Json

data class Parking(
    @field:Json(name = "_id")
    val id: String,
    val name: String,
    val lat: Double,
    val long: Double,
    // var vehicles: List<Vehicle>?
)