package com.example.appto.models

import com.squareup.moshi.Json
import java.util.*

data class Rental(
    @field:Json(name = "_id") val id: String,
    val vehicle: Vehicle?,
    val parkingOrigin: Parking?,
    val parkingDestination: Parking?,
    val finalPrice: Float?,
    val returnDate: Date?,
    val withdrawalDate: Date?
)