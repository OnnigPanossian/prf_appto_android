package com.example.appto.services

import com.example.appto.clients.Retrofit
import com.example.appto.models.Rental
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface RentalService {

    @POST("rental/pay/{id}")
    suspend fun pay(
        @Path("id") rental: String,
        @Header("Authorization") token: String?
    ): Response<Rental>
}

val rentalService: RentalService = Retrofit.restClient.create(RentalService::class.java)
