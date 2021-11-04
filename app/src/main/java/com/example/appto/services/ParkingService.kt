package com.example.appto.services

import com.example.appto.clients.Retrofit
import com.example.appto.models.Parking
import retrofit2.http.GET
import retrofit2.http.Path

interface ParkingService {
    @GET("parking")
    suspend fun getAll(): MutableList<Parking>

    @GET("parking/{id}")
    fun getById(@Path("id") vehicle: String): String
}

val parkingService: ParkingService = Retrofit.restClient.create(ParkingService::class.java)