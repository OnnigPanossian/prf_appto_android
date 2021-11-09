package com.example.appto.services

import com.example.appto.clients.Retrofit.Companion.restClient
import com.example.appto.models.Vehicle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VehicleService {
    @GET("vehicles")
    suspend fun getAll(): MutableList<Vehicle>

    @GET("parking/{id}/vehicles")
    suspend fun getVehiclesByParkingId(@Path("id") id: String): MutableList<Vehicle>

    @GET("vehicles/{id}")
    fun getById(@Path("id") vehicle: String): String

    @POST("vehicles/{id}/book")
    suspend fun book(@Path("id") vehicle: String): Response<Void>
}

val vehicleService: VehicleService = restClient.create(VehicleService::class.java)