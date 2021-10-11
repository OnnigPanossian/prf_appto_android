package com.example.appto

class Vehicle(
    val id: String,
    val brand: String,
    val model: String,
    val year: Int,
    val category: String,
    var rating: Float = 0F,
    var countRating: Int = 0
) {
}