package com.example.appto.services

import com.example.appto.clients.Retrofit
import com.example.appto.models.Parking
import retrofit2.http.*

interface ParkingService {
    @GET("parking")
    suspend fun getAll(@Query("category") category: String): MutableList<Parking>

    @GET("parking/{id}")
    fun getById(@Path("id") parking: String): String

    @DELETE("parking/{id}/vehicle/{vehicle}")
    fun deleteVehicle(@Path("id") parking: String, @Path("vehicle") vehicle: String): String

    @POST("parking/{id}/vehicle/{vehicle}")
    fun addVehicle(@Path("id") parking: String, @Path("vehicle") vehicle: String): String
}

val parkingService: ParkingService = Retrofit.restClient.create(ParkingService::class.java)