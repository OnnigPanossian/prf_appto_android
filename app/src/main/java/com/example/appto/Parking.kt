package com.example.appto

data class Parking(val name: String, val lat: Double, val long: Double, var vehicles: List<Vehicle>?) {
}