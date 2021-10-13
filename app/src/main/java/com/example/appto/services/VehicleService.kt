package com.example.appto.services

import com.example.appto.Vehicle
import com.example.appto.clients.Retrofit.Companion.restClient
import retrofit2.http.GET
import retrofit2.http.Path

interface VehicleService {
    @GET("vehicles")
    suspend fun getAll(): MutableList<Vehicle>

    @GET("vehicles/{id}")
    fun getById(@Path("id") vehicle: String): String
}

val vehicleService: VehicleService = restClient.create(VehicleService::class.java)