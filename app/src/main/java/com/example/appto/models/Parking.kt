package com.example.appto.models

data class Parking(
    val name: String,
    val lat: Double,
    val long: Double,
    var vehicles: List<Vehicle>?
)