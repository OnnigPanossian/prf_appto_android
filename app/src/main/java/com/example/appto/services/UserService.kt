package com.example.appto.services

import com.example.appto.clients.Retrofit
import com.example.appto.models.User
import okhttp3.RequestBody
import retrofit2.http.*

interface UserService {
    @POST("users")
    suspend fun register(@Body user: RequestBody): User

    @POST("users/login")
    suspend fun login(@Body user: User): User

    @POST("users/logout")
    suspend fun logout(@Header("Authorization") token: String): Void

    @GET("users/me")
    suspend fun getUser(@Header("Authorization") token: String): User

    @DELETE("users/me")
    suspend fun delete(@Header("Authorization") token: String): Void
}

val userService: UserService = Retrofit.restClient.create(UserService::class.java)