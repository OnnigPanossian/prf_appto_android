package com.example.appto.services

import com.example.appto.clients.Retrofit.Companion.restClient
import com.example.appto.models.Vehicle
import retrofit2.Response
import retrofit2.http.*

interface VehicleService {
    @GET("vehicles")
    suspend fun getAll(): MutableList<Vehicle>

    @GET("parking/{id}/vehicles")
    suspend fun getVehiclesByParkingId(@Path("id") id: String): MutableList<Vehicle>

    @GET("vehicles/{id}")
    fun getById(@Path("id") vehicle: String): String

    @POST("vehicles/{id}/book")
    suspend fun book(@Header("Authorization") token: String?, @Path("id") vehicle: String): Response<Void>

    @PUT("vehicles/{id}/calificate/{rating}")
    suspend fun qualification(@Path("id") vehicles: String, @Path("rating") rating: Float): Response<Void>
}

val vehicleService: VehicleService = restClient.create(VehicleService::class.java)