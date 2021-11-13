package com.example.appto.models

import com.squareup.moshi.Json
import java.util.*

data class Rental(
    @field:Json(name = "_id")
    val id: String,
    @field:Json(name = "vehicle")
    val vehicle: Vehicle,
    @field:Json(name = "user")
    val user: User,
    @field:Json(name = "parkingOriginId")
    val parkingOriginId: Parking,
    @field:Json(name = "parkingDestinationId")
    val parkingDestinationId: Parking,
    @field:Json(name = "finalPrice")
    val finalPrice: Double,
)