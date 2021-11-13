package com.example.appto.models

import com.squareup.moshi.Json

data class Rental(
    @field:Json(name = "_id")
    val id: String,
    val vehicle: Vehicle?,
    val user: User?,
    val parkingOrigin: Parking?,
    val parkingDestination: Parking?,
    val finalPrice: Float?,
    val returnDate: String?,
    val withdrawalDate: String?
)